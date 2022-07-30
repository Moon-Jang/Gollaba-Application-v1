package kr.mj.gollaba.auth;

import kr.mj.gollaba.common.Const;
import kr.mj.gollaba.auth.service.AuthService;
import kr.mj.gollaba.auth.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    public static final String BEARER_PREFIX = "Bearer ";

    private final JwtTokenUtils jwtTokenUtils;
    private final AuthService authService;

    // 실제 필터링 로직은 doFilterInternal 에 들어감
    // JWT 토큰의 인증 정보를 현재 쓰레드의 SecurityContext 에 저장하는 역할 수행
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // 1. Request Header 에서 토큰을 꺼냄
        String jwt = resolveToken(request);

        // 2. validateToken 으로 토큰 유효성 검사
        // 정상 토큰이면 해당 토큰으로 Authentication 을 가져와서 SecurityContext 에 저장
        if (StringUtils.hasText(jwt) && jwtTokenUtils.validateToken(jwt)) {
            Authentication authentication = generateAuthentication(request, jwtTokenUtils.getPayloadByKey(jwt, "email"));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private Authentication generateAuthentication(HttpServletRequest request, String email) {
        UserDetails userDetails = authService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken authentication
                = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        return authentication;
    }

    // Request Header 에서 토큰 정보를 꺼내오기
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(Const.ACCESS_TOKEN_HEADER);

        if (!StringUtils.hasText(bearerToken) || !bearerToken.startsWith(BEARER_PREFIX)) {
            return null;
        }

        return bearerToken.substring(7);
    }
}
