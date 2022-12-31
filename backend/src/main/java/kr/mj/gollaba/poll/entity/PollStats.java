package kr.mj.gollaba.poll.entity;

import kr.mj.gollaba.common.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PollStats extends BaseTimeEntity {

    @Id
    @Column(name = "poll_stats_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_id" , nullable = false)
    private Poll poll;

    @Column(name = "daily_vote_count", nullable = false)
    private String dailyVoteCount;

    @Column(name = "daily_view_count", nullable = false)
    private String dailyViewCount;

    @Builder
    private PollStats(Poll poll, String dailyVoteCount, String dailyViewCount) {
        this.poll = poll;
        this.dailyVoteCount = dailyVoteCount;
        this.dailyViewCount = dailyViewCount;
    }
}
