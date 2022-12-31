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
public class PollView extends BaseTimeEntity {

    @Id
    @Column(name = "poll_view_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_id" , nullable = false)
    private Poll poll;

    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @Builder
    private PollView(Poll poll, String ipAddress) {
        this.poll = poll;
        this.ipAddress = ipAddress;
    }

    public static PollView of(Poll poll, String ipAddress) {
        return new PollView(poll, ipAddress);
    }
}
