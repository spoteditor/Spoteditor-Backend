package com.spoteditor.backend.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

	@Bean
	public OpenAPI openAPI() {
		String cookieSchemeName = "cookieAuth";

		SecurityScheme securityScheme = new SecurityScheme()
				.type(SecurityScheme.Type.APIKEY)
				.in(SecurityScheme.In.COOKIE)
				.name("AccessToken");

		return new OpenAPI()
				.info(new Info()
						.title("Spot Editor API")
						.version("v1.0")
						.description("Spot Editor 서비스의 API 문서"))
				.addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement()
						.addList(cookieSchemeName))
				.components(new Components()
						.addSecuritySchemes(cookieSchemeName, securityScheme));
	}
}