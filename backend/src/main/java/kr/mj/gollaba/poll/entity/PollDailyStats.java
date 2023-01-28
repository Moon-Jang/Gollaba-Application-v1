package kr.mj.gollaba.poll.entity;

import kr.mj.gollaba.common.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PollDailyStats extends BaseTimeEntity {

    @Id
    @Column(name = "poll_daily_stats_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "poll_id" , nullable = false)
    private Poll poll;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "daily_vote_count", nullable = false)
    private Integer dailyVoteCount;

    @Column(name = "daily_read_count", nullable = false)
    private Integer dailyReadCount;

    @Column(name = "daily_favorites_count", nullable = false)
    private Integer dailyFavoritesCount;

    @Builder
    private PollDailyStats(Long id, Poll poll, LocalDate date, Integer dailyVoteCount, Integer dailyReadCount, Integer dailyFavoritesCount) {
        this.id = id;
        this.poll = poll;
        this.date = date;
        this.dailyVoteCount = dailyVoteCount;
        this.dailyReadCount = dailyReadCount;
        this.dailyFavoritesCount = dailyFavoritesCount;
    }
}
