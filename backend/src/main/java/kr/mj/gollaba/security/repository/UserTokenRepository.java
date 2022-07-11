package kr.mj.gollaba.security.repository;

import kr.mj.gollaba.security.entity.UserToken;
import kr.mj.gollaba.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    Optional<UserToken> findByRefreshToken(String refreshToken);

    void deleteByRefreshToken(String refreshToken);

}
