package kr.mj.gollaba.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtils {

    private final String secretKey;

    public JwtTokenUtils(@Value("${security.jwt.secret-key}") String secretKey) {
        this.secretKey = secretKey;
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
            throw new GollabaException(GollabaErrorCode.INVALID_JWT_TOKEN);
        }
    }

}
