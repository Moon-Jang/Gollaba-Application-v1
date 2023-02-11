package kr.mj.gollaba.favorites.repository;

import kr.mj.gollaba.favorites.entity.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FavoritesRepository extends JpaRepository<Favorites, Long> {

    @Query("SELECT f FROM Favorites f JOIN FETCH f.user WHERE f.id = :id")
    Optional<Favorites> findById(@Param("id") Long id);

    List<Favorites> findAllByUserId(Long userId);

    Integer countByPollId(Long pollId);

}
