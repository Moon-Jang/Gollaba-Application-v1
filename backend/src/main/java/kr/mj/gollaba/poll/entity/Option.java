package kr.mj.gollaba.poll.entity;

import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "poll_option")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Option {

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

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

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
