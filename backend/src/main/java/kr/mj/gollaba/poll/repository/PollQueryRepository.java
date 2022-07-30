package kr.mj.gollaba.poll.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mj.gollaba.poll.dto.PollQueryFilter;
import kr.mj.gollaba.poll.entity.Poll;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static kr.mj.gollaba.poll.entity.QPoll.poll;
import static kr.mj.gollaba.poll.entity.QOption.option;
import static kr.mj.gollaba.user.entity.QUser.user;
@Repository
@RequiredArgsConstructor
public class PollQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public int findAllCount(PollQueryFilter filter) {
        return jpaQueryFactory.selectFrom(poll)
                .join(poll.options, option).fetchJoin()
                .leftJoin(poll.user, user).fetchJoin()
                .where(eqUserId(filter.getUserId()),
                        likeTitle(filter.getTitle()))
                .fetch().size();
    }

    public List<Poll> findAll(PollQueryFilter filter) {
        return jpaQueryFactory.selectFrom(poll)
                .join(poll.options, option).fetchJoin()
                .leftJoin(poll.user, user).fetchJoin()
                .where(eqUserId(filter.getUserId()),
                        likeTitle(filter.getTitle()))
                .limit(filter.getLimit())
                .offset(filter.getOffset())
                .fetch();
    }

    public Poll findById(Long id) {
        return jpaQueryFactory.selectFrom(poll)
                .join(poll.options, option).fetchJoin()
                .leftJoin(poll.user, user).fetchJoin()
                .where(poll.id.eq(id))
                .fetchOne();
    }


    private BooleanExpression eqUserId(Long userId) {
        if (userId == null) {
            return null;
        }

        return poll.user.id.eq(userId);
    }

    private BooleanExpression likeTitle(String title) {
        if (StringUtils.isNullOrEmpty(title)) {
            return null;
        }

        return poll.title.like(title);
    }
}
