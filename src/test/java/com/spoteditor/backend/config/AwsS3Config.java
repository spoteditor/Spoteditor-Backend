package com.spoteditor.backend.config;

import com.amazonaws.services.s3.AmazonS3;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

// 가짜 S3 설정 클래스
@TestConfiguration
public class AwsS3Config {

	@Bean
	@Primary	// 빈 우선순위로 등록
	public AmazonS3 test() {
		return Mockito.mock(AmazonS3.class);
	}
}
