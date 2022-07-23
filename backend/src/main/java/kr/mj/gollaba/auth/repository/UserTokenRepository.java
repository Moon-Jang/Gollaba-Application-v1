package kr.mj.gollaba.auth.repository;

import kr.mj.gollaba.auth.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    Optional<UserToken> findByRefreshToken(String refreshToken);

    boolean existsByRefreshToken(String refreshToken);

    void deleteByRefreshToken(String refreshToken);

}
