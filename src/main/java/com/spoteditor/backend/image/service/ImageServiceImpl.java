package com.spoteditor.backend.image.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.spoteditor.backend.global.exception.PlaceException;
import com.spoteditor.backend.image.controller.dto.PresignedUrlRequest;
import com.spoteditor.backend.image.controller.dto.PresignedUrlResponse;
import com.spoteditor.backend.image.entity.PlaceImage;
import com.spoteditor.backend.image.repository.ImageRepository;
import com.spoteditor.backend.place.entity.Place;
import com.spoteditor.backend.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;
import java.util.UUID;

import static com.spoteditor.backend.global.response.ErrorCode.NOT_FOUND_PLACE;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

	@Value("${aws-resource.s3-temp.temp-bucket-name}")
	private String bucketName;

	@Value("${aws-resource.s3.bucket-name}")
	private String mainBucketName;

	private final AmazonS3 s3Client;
	private final PlaceRepository placeRepository;
	private final ImageRepository imageRepository;

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

	private String generatePresignedUrlRequest(String bucketName, String fileName) {
		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName)
				.withMethod(HttpMethod.PUT)
				.withExpiration(getExpirationDate());
		URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
		return url.toString();
	}

	@Override
	public PresignedUrlResponse processPresignedUrl(PresignedUrlRequest request) {

		String url = createPath(request.originalFile());
		String presignedUrl = generatePresignedUrlRequest(bucketName, url);	// 임시 디렉터리
		return PresignedUrlResponse.from(presignedUrl, url);
	}

	@Override
	public void upload(String originalFile, String path, Long placeId) {
		String mainPath = "places/" + placeId + "/" + path;	// 메인 디렉터리(공개X)

		CopyObjectRequest copyObjectRequest = new CopyObjectRequest(
				bucketName, path,
				mainBucketName, mainPath
		);
		s3Client.copyObject(copyObjectRequest);

		DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(
				bucketName, path
		);
		s3Client.deleteObject(deleteObjectRequest);

		Place place = placeRepository.findById(placeId)
				.orElseThrow(() -> new PlaceException(NOT_FOUND_PLACE));

		PlaceImage image = PlaceImage.builder()
				.place(place)
				.originalFile(originalFile)
				.storedFile(mainPath)
				.build();
		imageRepository.save(image);
	}

}
