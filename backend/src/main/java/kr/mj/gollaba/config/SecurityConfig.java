package kr.mj.gollaba.config;

import kr.mj.gollaba.auth.JwtAuthenticationEntryPoint;
import kr.mj.gollaba.auth.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final JwtTokenFilter jwtTokenFilter;

    private final String[] permitAllList = {
            "/v1/signup",
            "/v1/login",
            "/v1/polls",
            "/health-check", // aws - target group
            "/h2-console/**", // h2-console
            "/v3/api-docs", // swagger
            "/swagger-ui/**", // swagger
            "/swagger-resources/**", // swagger
            "/webjars/**", // swagger
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(permitAllList).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().disable().headers().frameOptions().disable(); //h2 console 관련

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

}