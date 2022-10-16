package kr.mj.gollaba.favorites.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mj.gollaba.favorites.dto.FavoritesUniqueIndexDto;
import kr.mj.gollaba.favorites.entity.Favorites;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static kr.mj.gollaba.favorites.entity.QFavorites.favorites;

@Repository
@RequiredArgsConstructor
public class FavoritesQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Favorites> findAllByUniqueIndex(List<FavoritesUniqueIndexDto> dtos) {
        return jpaQueryFactory.select(favorites)
                .from(favorites)
                .where(eqUniqueIndexIn(dtos))
                .fetch();
    }

    private BooleanExpression eqUniqueIndexIn(List<FavoritesUniqueIndexDto> dtos) {
        Expression[] tuples = dtos.stream()
                .map(dto -> Expressions.template(Object.class, "({0}, {1})", dto.getPollId(), dto.getUserId()))
                .toArray(Expression[]::new);

        return Expressions.list(favorites.poll.id, favorites.user.id).in(tuples);
    }
}
