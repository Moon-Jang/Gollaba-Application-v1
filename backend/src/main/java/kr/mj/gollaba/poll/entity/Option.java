package kr.mj.gollaba.poll.entity;

import kr.mj.gollaba.common.entity.BaseTimeEntity;
import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "poll_option")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Option extends BaseTimeEntity {

    @Id
    @Column(name = "poll_option_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_id" , nullable = true)
    private Poll poll;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL)
    private Set<Voter> voters = new HashSet<>();

    @Builder
    private Option(Long id, String description) {
        this.id = id;
        this.description = description;
    }
    public void updateDescription(String description) {
        this.description = description;
    }

    public void setPoll(Poll poll) { this.poll = poll; }

    public Voter findVoterByVoterId(Long voterId) {
        return voters.stream()
                .filter(voter -> voter.getId().equals(voterId))
                .findFirst()
                .orElseThrow(() -> new GollabaException(GollabaErrorCode.NOT_EXIST_VOTER));
    }
}
