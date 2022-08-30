package kr.mj.gollaba.poll.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mj.gollaba.poll.dto.PollQueryFilter;
import kr.mj.gollaba.poll.entity.Poll;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static kr.mj.gollaba.poll.entity.QPoll.poll;
import static kr.mj.gollaba.poll.entity.QOption.option;
import static kr.mj.gollaba.poll.entity.QVoter.voter;
import static kr.mj.gollaba.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class PollQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public long findAllCount(PollQueryFilter filter) {
        return jpaQueryFactory.select(poll.countDistinct())
                .from(poll)
                .join(poll.options, option)
                .leftJoin(poll.user, user)
                .leftJoin(option.voters, voter)
                .where(eqUserId(filter.getUserId()),
                        likeTitle(filter.getTitle()))
                .fetchOne();
    }



    public List<Long> findIds(PollQueryFilter filter) {
        return jpaQueryFactory.select(poll.id)
                .from(poll)
                .join(poll.options, option)
                .leftJoin(poll.user, user)
                .leftJoin(option.voters, voter)
                .where(eqUserId(filter.getUserId()),
                        likeTitle(filter.getTitle()))
                .groupBy(poll.id)
                .orderBy(poll.id.desc())
                .limit(filter.getLimit())
                .offset(filter.getOffset())
                .fetch();
    }

    public List<Poll> findAll(List<Long> ids) {
        return jpaQueryFactory.selectFrom(poll).distinct()
                .join(poll.options, option).fetchJoin()
                .leftJoin(poll.user, user).fetchJoin()
                .leftJoin(option.voters, voter).fetchJoin()
                .where(poll.id.in(ids))
                .orderBy(poll.id.desc())
                .fetch();
    }

    public Optional<Poll> findById(Long id) {
        Poll foundPoll = jpaQueryFactory.selectFrom(poll)
                .join(poll.options, option).fetchJoin()
                .leftJoin(poll.user, user).fetchJoin()
                .leftJoin(option.voters, voter).fetchJoin()
                .where(poll.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(foundPoll);
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

        return poll.title.like("%" + title + "%");
    }
}
