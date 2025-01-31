package com.spoteditor.backend.image.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.spoteditor.backend.global.exception.ImageException;
import com.spoteditor.backend.global.exception.PlaceException;
import com.spoteditor.backend.image.controller.dto.PlaceImageResponse;
import com.spoteditor.backend.image.controller.dto.PreSignedUrlResponse;
import com.spoteditor.backend.image.controller.dto.PresignedUrlRequest;
import com.spoteditor.backend.image.entity.PlaceImage;
import com.spoteditor.backend.image.repository.PlaceImageRepository;
import com.spoteditor.backend.place.entity.Place;
import com.spoteditor.backend.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;
import java.util.UUID;

import static com.spoteditor.backend.global.response.ErrorCode.NOT_FOUND_IMAGE;
import static com.spoteditor.backend.global.response.ErrorCode.NOT_FOUND_PLACE;
import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;

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
	public PreSignedUrlResponse processPresignedUrl(PresignedUrlRequest request) {

		String url = request.originalFile();	// PreSigned URL 발급
		String preSignedUrl = generatePreSignedUrlRequest(bucketName, url);
		return PreSignedUrlResponse.from(preSignedUrl);
	}

	@Override
	public PlaceImageResponse upload(String originalFile, Long placeId) {
		String storedPath = createPath(originalFile);
		String result = "places/" + placeId + "/" + storedPath;	// S3 메인 디렉터리에 저장되는 경로

		copyImageToMainBucket(originalFile, result);
		deleteImageFromTempBucket();

		Place place = placeRepository.findById(placeId)
				.orElseThrow(() -> new PlaceException(NOT_FOUND_PLACE));

		PlaceImage image = PlaceImage.builder()
				.place(place)
				.originalFile(originalFile)
				.storedFile(storedPath)
				.build();

		place.addPlaceImage(image);
		PlaceImage savedImage = imageRepository.save(image);
		return PlaceImageResponse.from(savedImage);
	}

	private void deleteImageFromTempBucket() {
		DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(
				bucketName, path
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

		System.out.println(bucketName);
		System.out.println(sourcePath);
		System.out.println(mainBucketName);
		System.out.println(destinationPath);

		try {
			s3Client.copyObject(copyObjectRequest);
		} catch (AmazonS3Exception e) {
			throw new ImageException(NOT_FOUND_IMAGE);
		}
	}

}
