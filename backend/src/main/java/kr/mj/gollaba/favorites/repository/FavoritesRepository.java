package kr.mj.gollaba.favorites.repository;

import kr.mj.gollaba.favorites.entity.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritesRepository extends JpaRepository<Favorites, Long> {
}
