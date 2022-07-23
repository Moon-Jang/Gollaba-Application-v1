package kr.mj.gollaba.poll.entity;

import kr.mj.gollaba.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Voter {

    @Id
    @Column(name = "voter_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id" , nullable = false)
    private Option option;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , nullable = true)
    private User user;

    @Column(name = "voter_name", nullable = false)
    private String voterName;

    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Voter(Long id, Option option, User user, String voterName, String ipAddress) {
        this.id = id;
        this.option = option;
        this.user = user;
        this.voterName = voterName;
        this.ipAddress = ipAddress;
    }

    public void vote(Option option) {
        this.option = option;
        option.getVoters().add(this);
    }

}
