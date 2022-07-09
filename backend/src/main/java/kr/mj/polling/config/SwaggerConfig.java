package kr.mj.polling.config;

import io.swagger.v3.oas.models.OpenAPI;
import kr.mj.polling.common.Const;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.web.OpenApiTransformationContext;
import springfox.documentation.oas.web.WebMvcOpenApiTransformationFilter;
import springfox.documentation.service.*;
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
	public Docket apiV1() {
		version = "v1";
		title = "Free-Polling API";

		Server localServer = new Server("local", "http://localhost:8080", "for local", Collections.emptyList(), Collections.emptyList());
		Server devServer = new Server("dev", "http://dev.free.polling.com", "for dev", Collections.emptyList(), Collections.emptyList());
		Server prodServer = new Server("prod", "https://free.polling.com", "for prod", Collections.emptyList(), Collections.emptyList());

		return new Docket(DocumentationType.OAS_30)
				.servers(localServer, devServer, prodServer)
				.consumes(getConsumeContentTypes())
				.produces(getProduceContentTypes())
				.useDefaultResponseMessages(false)
				.groupName(version)
				.select()
				.apis(RequestHandlerSelectors.basePackage("kr.mj.polling.*.controller"))
				.paths(PathSelectors.ant(Const.ROOT_URL + "/**"))
				.build()
				.apiInfo(apiInfo(title, version))
				.securityContexts(Arrays.asList(securityContext()))
				.securitySchemes(Arrays.asList(apiKey()));
	}

	@Override
	public OpenAPI transform(OpenApiTransformationContext<HttpServletRequest> context) {

		OpenAPI openApi = context.getSpecification();

		io.swagger.v3.oas.models.servers.Server localServer = new io.swagger.v3.oas.models.servers.Server();
		localServer.setDescription("local");
		localServer.setUrl("http://localhost:8080");

		io.swagger.v3.oas.models.servers.Server devServer = new io.swagger.v3.oas.models.servers.Server();
		devServer.setDescription("dev");
		devServer.setUrl("http://api-partner-1a.howser.dev:8080");

		io.swagger.v3.oas.models.servers.Server prodServer = new io.swagger.v3.oas.models.servers.Server();
		prodServer.setDescription("prod");
		prodServer.setUrl("http://api-partner-1a.howser.prod:8080");

		openApi.setServers(Arrays.asList(localServer, devServer, prodServer));

		return openApi;
	}

	@Override
	public boolean supports(DocumentationType documentationType) {
		return documentationType.equals(DocumentationType.OAS_30);
	}

	private ApiInfo apiInfo(String title, String version) {
        return new ApiInfo(
				title,
				"Swagger로 생성한 API Docs",
				version,
				null,
				null,
				null,
				null,
				new ArrayList<>());
    }
    
	private ApiKey apiKey() {
		return new ApiKey("JWT", "Authorization", "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth())
				.forPaths(PathSelectors.any()).build();
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
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

