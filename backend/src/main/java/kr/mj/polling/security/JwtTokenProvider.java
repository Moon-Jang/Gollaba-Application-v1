package kr.mj.polling.security;

import io.jsonwebtoken.*;
import kr.mj.polling.exception.PollingAppErrorCode;
import kr.mj.polling.exception.PollingAppException;
import kr.mj.polling.security.service.PrincipalDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String secretKey;
    private final long accessExpirationTime;
    private final long refreshExpirationTime;
    private final PrincipalDetailsService principalDetailsService;

    public JwtTokenProvider(
            @Value("${security.jwt.secret-key}") String secretKey,
            @Value("${security.jwt.access-expiration-time}") long accessExpirationTime,
            @Value("${security.jwt.refresh-expiration-time}") long refreshExpirationTime,
            PrincipalDetailsService principalDetailsService
    ) {
        this.secretKey = secretKey;
        this.accessExpirationTime = accessExpirationTime;
        this.refreshExpirationTime = refreshExpirationTime;
        this.principalDetailsService = principalDetailsService;
    }

    public String createToken(String email) {
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + accessExpirationTime);

        return Jwts.builder()
                .claim("email", email)
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getPayloadByKey(String token, String key) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .get(key, String.class);
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException();
        }
    }

    /*
        iss : 이 데이터의 발행자를 뜻합니다.
        iat : 이 데이터가 발행된 시간을 뜻합니다.
        exp : 이 데이터가 만료된 시간을 뜻합니다.
        sub : 토큰의 제목입니다.
        aud : 토큰의 대상입니다.
        nbf : 토큰이 처리되지 않아야 할 시점을 의미합니다.
        이 시점이 지나기 전엔 토큰이 처리되지 않습니다.
        jti : 토큰의 고유 식별자입니다.
     */
    public Authentication getAuthentication(String token) {
        Claims claims = getClaimsByToken(token);
        return null;
    }

    private Claims getClaimsByToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            throw new PollingAppException(PollingAppErrorCode.INVALID_JWT_TOKEN);
        }
    }
}
