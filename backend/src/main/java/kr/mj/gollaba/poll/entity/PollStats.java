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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "poll_id" , nullable = false)
    private Poll poll;

    @Column(name = "total_vote_count", nullable = false)
    private Integer totalVoteCount;

    @Column(name = "total_read_count", nullable = false)
    private Integer totalReadCount;

    @Column(name = "total_favorites_count", nullable = false)
    private Integer totalFavoritesCount;

    @Builder
    private PollStats(Long id, Poll poll, Integer totalVoteCount, Integer totalReadCount, Integer totalFavoritesCount) {
        this.id = id;
        this.poll = poll;
        this.totalVoteCount = totalVoteCount;
        this.totalReadCount = totalReadCount;
        this.totalFavoritesCount = totalFavoritesCount;
    }

    public static PollStats create(Poll poll) {
        return PollStats.builder()
            .poll(poll)
            .totalVoteCount(0)
            .totalReadCount(0)
            .totalFavoritesCount(0)
            .build();
    }

    public void updateTotalVoteCount(Integer totalVoteCount) {
        this.totalVoteCount = totalVoteCount;
    }

    public void updateTotalReadCount(Integer totalReadCount) {
        this.totalReadCount = totalReadCount;
    }

    public void updateTotalFavoritesCount(Integer totalFavoritesCount) {
        this.totalFavoritesCount = totalFavoritesCount;
    }
}
