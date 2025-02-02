package com.spoteditor.backend.image.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.spoteditor.backend.global.exception.ImageException;
import com.spoteditor.backend.global.exception.PlaceException;
import com.spoteditor.backend.image.controller.dto.PlaceImageResponse;
import com.spoteditor.backend.image.controller.dto.PreSignedUrlResponse;
import com.spoteditor.backend.image.controller.dto.PreSignedUrlRequest;
import com.spoteditor.backend.image.entity.PlaceImage;
import com.spoteditor.backend.image.repository.PlaceImageRepository;
import com.spoteditor.backend.place.entity.Place;
import com.spoteditor.backend.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.spoteditor.backend.global.response.ErrorCode.NOT_FOUND_IMAGE;
import static com.spoteditor.backend.global.response.ErrorCode.NOT_FOUND_PLACE;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceImageServiceImpl implements PlaceImageService {

	@Value("${aws-resource.s3-temp.temp-bucket-name}")
	private String bucketName;

	@Value("${aws-resource.s3.bucket-name}")
	private String mainBucketName;

	private final AmazonS3 s3Client;
	private final PlaceRepository placeRepository;
	private final PlaceImageRepository imageRepository;
	private final RedisTemplate<String, String> redisTemplate;

	private static final String FILE_KEY_PREFIX = "image:key:";
	private static final long FILE_KEY_EXPIRATION = TimeUnit.MINUTES.toSeconds(5);

	private String createPath(String originalFile) {
		StringBuilder sb = new StringBuilder();
		return sb.append(UUID.randomUUID()).append("_").append(originalFile).toString();
	}

	private Date getExpirationDate() {
		Date expiration = new Date();
		long expTimeMillis = expiration.getTime();
		expTimeMillis += 1000 * 60 * 2;
		expiration.setTime(expTimeMillis);
		return expiration;
	}

	private String generatePreSignedUrlRequest(String bucketName, String fileName) {
		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName)
				.withMethod(HttpMethod.PUT)
				.withExpiration(getExpirationDate());
		URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
		return url.toString();
	}

	@Override
	public PreSignedUrlResponse processPreSignedUrl(PreSignedUrlRequest request) {
		String uuid = UUID.randomUUID().toString();
		String tempPath = createPath(request.originalFile());

		StringBuilder sb = new StringBuilder();
		String key = sb.append(FILE_KEY_PREFIX).append(uuid).append(request.originalFile()).toString();

		redisTemplate.opsForValue().set(key, tempPath, FILE_KEY_EXPIRATION, TimeUnit.SECONDS);
		String preSignedUrl = generatePreSignedUrlRequest(bucketName, tempPath);
		return PreSignedUrlResponse.from(preSignedUrl, uuid);
	}

	@Override
	public PlaceImageResponse upload(String originalFile, String uuid, Long placeId) {

		String tempPath = redisTemplate.opsForValue().get(FILE_KEY_PREFIX + uuid + originalFile);
		validateValue(tempPath);

		String mainPath = createPath(originalFile);	// S3 메인 버킷에 저장되는 경로명 생성
		copyImageToMainBucket(tempPath, mainPath);
		deleteImageFromTempBucket(tempPath);

		Place place = placeRepository.findById(placeId)
				.orElseThrow(() -> new PlaceException(NOT_FOUND_PLACE));

		PlaceImage image = PlaceImage.builder()
				.place(place)
				.originalFile(originalFile)
				.storedFile(mainPath)
				.build();

		place.addPlaceImage(image);
		PlaceImage savedImage = imageRepository.save(image);
		return PlaceImageResponse.from(savedImage);
	}

	private void validateValue(String value) {
		if (value == null) {
			throw new ImageException(NOT_FOUND_IMAGE);
		}
	}

	private void deleteImageFromTempBucket(String tempPath) {
		DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(
				bucketName, tempPath
		);

		try {
			s3Client.deleteObject(deleteObjectRequest);
		} catch (AmazonS3Exception e) {
			throw new ImageException(NOT_FOUND_IMAGE);
		}
	}

	private void copyImageToMainBucket(String sourcePath, String destinationPath) {
		CopyObjectRequest copyObjectRequest = new CopyObjectRequest(
				bucketName, sourcePath,
				mainBucketName, destinationPath
		);

		try {
			s3Client.copyObject(copyObjectRequest);
		} catch (AmazonS3Exception e) {
			throw new ImageException(NOT_FOUND_IMAGE);
		}
	}
}