package kr.mj.gollaba.config;

import com.fasterxml.classmate.TypeResolver;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.servers.Server;
import kr.mj.gollaba.common.Const;
import kr.mj.gollaba.common.ErrorAPIResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.web.OpenApiTransformationContext;
import springfox.documentation.oas.web.WebMvcOpenApiTransformationFilter;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Configuration
public class SwaggerConfig implements WebMvcOpenApiTransformationFilter {
	
	private String version;
    private String title;

	@Bean
	public Docket apiV1(TypeResolver typeResolver) {
		version = "v1";
		title = "Gollaba API";

		return new Docket(DocumentationType.OAS_30)
				.additionalModels(
						typeResolver.resolve(ErrorAPIResponse.class)
				)
				.consumes(getConsumeContentTypes())
				.produces(getProduceContentTypes())
				.useDefaultResponseMessages(false)
				.groupName(version)
				.select()
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
				.paths(PathSelectors.ant(Const.ROOT_URL + "/**"))
				.build()
				.apiInfo(apiInfo(title, version))
				.securityContexts(List.of(securityContext()))
				.securitySchemes(List.of(accessToken(), refreshToken()));
	}

	@Override
	public OpenAPI transform(OpenApiTransformationContext<HttpServletRequest> context) {

		OpenAPI openApi = context.getSpecification();

		Server localServer = new Server();
		localServer.setDescription("local");
		localServer.setUrl("http://localhost:8080");

		Server devServer = new Server();
		devServer.setDescription("dev");
		devServer.setUrl("https://dev.api.gollaba.net");

		Server prodServer = new Server();
		prodServer.setDescription("prod");
		prodServer.setUrl("https://api.gollaba.net");

		openApi.setServers(List.of(localServer, devServer, prodServer));

		return openApi;
	}

	@Override
	public boolean supports(DocumentationType documentationType) {
		return documentationType.equals(DocumentationType.OAS_30);
	}

	private ApiInfo apiInfo(String title, String version) {
        return new ApiInfo(
				title,
				title + " Docs",
				version,
				null,
				null,
				null,
				null,
				new ArrayList<>());
    }

	private ApiKey accessToken() {
		return new ApiKey(Const.ACCESS_TOKEN_HEADER, Const.ACCESS_TOKEN_HEADER, "header");
	}

	private ApiKey refreshToken() {
		return new ApiKey(Const.REFRESH_TOKEN_HEADER, Const.REFRESH_TOKEN_HEADER,"header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
				.securityReferences(defaultAuth())
				.build();
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference(Const.ACCESS_TOKEN_HEADER, authorizationScopes), new SecurityReference(Const.REFRESH_TOKEN_HEADER, authorizationScopes));
	}
	
	private Set<String> getConsumeContentTypes() {
		Set<String> consumes = new HashSet<>();
		consumes.add("application/json;charset=UTF-8");
		consumes.add("application/x-www-form-urlencoded");
		return consumes;
	}
	
	private Set<String> getProduceContentTypes() {
		Set<String> produces = new HashSet<>();
		produces.add("application/json;charset=UTF-8");
		return produces;
	}

}

