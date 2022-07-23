package kr.mj.gollaba.poll.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mj.gollaba.poll.entity.Poll;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static kr.mj.gollaba.poll.entity.QPoll.poll;
import static kr.mj.gollaba.poll.entity.QOption.option;

@Repository
@RequiredArgsConstructor
public class PollQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Poll> findAll() {
        return null;
    }

    public Poll findById(Long id) {
        return jpaQueryFactory.selectFrom(poll)
                .join(poll.options, option).fetchJoin()
                .where(poll.id.eq(id))
                .fetchOne();
    }

}
