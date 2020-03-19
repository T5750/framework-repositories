package t5750.spring.swagger.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
// @Profile("dev")
public class SwaggerConfig {
	@Value("${spring.profiles.active}")
	private String activeProfile;

	@Bean
	public Docket userApi() {
		boolean enable = "dev".equals(activeProfile) ? true : false;
		return new Docket(DocumentationType.SWAGGER_2).enable(enable)
				.apiInfo(apiInfo()).select().build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Spring Swagger API").version("1.0")
				.build();
	}
}