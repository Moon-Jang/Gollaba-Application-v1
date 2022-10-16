package kr.mj.gollaba.unit.favorites.repository;

import kr.mj.gollaba.favorites.dto.FavoritesUniqueIndexDto;
import kr.mj.gollaba.favorites.entity.Favorites;
import kr.mj.gollaba.favorites.repository.FavoritesQueryRepository;
import kr.mj.gollaba.favorites.repository.FavoritesRepository;
import kr.mj.gollaba.unit.common.RepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FavoritesQueryRepositoryTest extends RepositoryTest {

    @Autowired
    private FavoritesQueryRepository favoritesQueryRepository;

    @Autowired
    private FavoritesRepository favoritesRepository;

    @DisplayName("즐겨찾기 전체 조회 By 유니크 인덱스")
    @Test
    void findAllByUniqueIndex() {
        //given
        var dtos = new ArrayList<FavoritesUniqueIndexDto>();
        var listSize = 3;

        for (int i = 1; i <= listSize; i++) {
            dtos.add(FavoritesUniqueIndexDto.of(i, i));
        }

        //when
        List<Favorites> favoritesList = favoritesQueryRepository.findAllByUniqueIndex(dtos);

        //then
        assertThat(favoritesList.size()).isEqualTo(listSize);
    }
}
