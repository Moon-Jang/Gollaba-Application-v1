package kr.mj.gollaba.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.mj.gollaba.user.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String secretKey;
    private final long accessExpirationTime;
    private final long refreshExpirationTime;

    public JwtTokenProvider(
            @Value("${security.jwt.secret-key}") String secretKey,
            @Value("${security.jwt.access-expiration-time}") long accessExpirationTime,
            @Value("${security.jwt.refresh-expiration-time}") long refreshExpirationTime
    ) {
        this.secretKey = secretKey;
        this.accessExpirationTime = accessExpirationTime;
        this.refreshExpirationTime = refreshExpirationTime;
    }

    public String createAccessToken(String uniqueId, String nickName) {
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + accessExpirationTime);

        return Jwts.builder()
                .claim("uid", uniqueId)
                .claim("un", nickName)
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createAccessToken(User user) {
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + accessExpirationTime);

        return Jwts.builder()
                .claim("id", user.getId())
                .claim("uid", user.getUniqueId())
                .claim("un", user.getNickName())
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createRefreshToken() {
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + refreshExpirationTime);

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
