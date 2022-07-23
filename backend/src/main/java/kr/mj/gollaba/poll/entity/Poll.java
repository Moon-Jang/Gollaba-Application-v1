package kr.mj.gollaba.poll.entity;

import kr.mj.gollaba.poll.type.PollingResponseType;
import kr.mj.gollaba.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Poll {

    @Id
    @Column(name = "poll_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = true)
    private User user;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "creater_name", nullable = false)
    private String creatorName;

    @Enumerated(EnumType.STRING)
    @Column(name ="response_type", nullable = false)
    private PollingResponseType responseType;

    @Column(name ="is_ballot", nullable = false)
    private Boolean isBallot;

    @Column(name = "ended_at", nullable = true)
    private LocalDateTime endedAt;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL)
    private List<Option> options = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Poll(Long id, String title, String creatorName, PollingResponseType responseType, Boolean isBallot, LocalDateTime endedAt) {
        this.id = id;
        this.title = title;
        this.creatorName = creatorName;
        this.responseType = responseType;
        this.isBallot = isBallot;
        this.endedAt = endedAt;
    }

    public void addPollingItem(Option option) {
        this.options.add(option);
        option.setPoll(this);
    }

    public void registerCreator(User user) {
        this.user = user;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public void updateResponseType(PollingResponseType responseType) {
        this.responseType = responseType;
    }

    public void updateEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }
}
