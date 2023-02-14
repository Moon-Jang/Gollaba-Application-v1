package kr.mj.gollaba.config;

import kr.mj.gollaba.auth.JwtAuthenticationEntryPoint;
import kr.mj.gollaba.auth.JwtTokenFilter;
import kr.mj.gollaba.auth.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final JwtTokenFilter jwtTokenFilter;
    public static final String REFRESH_TOKEN_HEADER = "GA-Refresh-Token";
    private final AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    private final String[] permitAllList = {
            "/v1/signup",
            "/v1/login",
            "/v1/polls",
            "/v1/polls/{pollId}",
            "/v1/vote",
            "/login/oauth2/code/**", // OAuth2 login
            "/health-check", // aws - target group
            "/h2-console/**", // h2-console
            "/v3/api-docs", // swagger
            "/swagger-ui/**", // swagger
            "/swagger-resources/**", // swagger
            "/webjars/**", // swagger
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.exceptionHandling()
            .authenticationEntryPoint(unauthorizedHandler);

        //h2 console 관련
        http.formLogin().disable()
            .headers().frameOptions().disable();

        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
            .antMatchers(permitAllList).permitAll();

        http.oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize")
                .authorizationRequestRepository(authorizationRequestRepository)
//            .and()
//                .redirectionEndpoint().baseUri("/oauth2/callback/*")
            .and()
                .userInfoEndpoint()
                .userService(new DefaultOAuth2UserService())
            .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(null);

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

}