package com.sandman.download.configuration.swaggerUI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Created by wangj on 2018/4/20.
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket buildDocket() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(buildApiInf()).select()
				.apis(RequestHandlerSelectors.basePackage("com.sandman.download.controller"))
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo buildApiInf() {
		return new ApiInfoBuilder().title("swagger2 UI构建API文档").contact("测试").version("1.0").build();
	}
}
