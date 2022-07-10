package kr.mj.gollaba.unit.repository;

import kr.mj.gollaba.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUniqueId(String uniqueId);

    boolean existsByNickName(String nickName);

    Optional<User> findByUniqueId(String uniqueId);

}
