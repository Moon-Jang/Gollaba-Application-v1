package kr.mj.gollaba.auth.repository;

import kr.mj.gollaba.auth.entity.UserProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserProviderRepository extends JpaRepository<UserProvider, Long> {

    @Query("SELECT up FROM UserProvider up JOIN FETCH up.user WHERE up.providerId = ?1")
    Optional<UserProvider> findByProviderId(String providerId);

    boolean existsByProviderId(String providerId);

}
