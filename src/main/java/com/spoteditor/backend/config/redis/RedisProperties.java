package com.spoteditor.backend.config.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.data.redis")
public class RedisProperties {

	private String host;
	private int port;
	private RedisProperties master;
	private List<RedisProperties> slaves;
}
