package kr.mj.gollaba.poll.entity;

import kr.mj.gollaba.common.entity.BaseTimeEntity;
import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import kr.mj.gollaba.poll.type.PollingResponseType;
import kr.mj.gollaba.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Poll extends BaseTimeEntity {

    @Id
    @Column(name = "poll_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "creator_name", nullable = false)
    private String creatorName;

    @Enumerated(EnumType.STRING)
    @Column(name = "response_type", nullable = false)
    private PollingResponseType responseType;

    @Column(name = "is_ballot", nullable = false)
    private Boolean isBallot;

    @Column(name = "ended_at", nullable = false)
    private LocalDateTime endedAt;

    @Column(name = "poll_image_url", nullable = true)
    private String pollImageUrl;

    @Column(name = "read_count", nullable = false)
    private Integer readCount;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL)
    @OrderColumn(name = "position")
    private List<Option> options = new ArrayList<>();

    @Builder
    private Poll(Long id, User user, String title, String creatorName, PollingResponseType responseType, Boolean isBallot, LocalDateTime endedAt, String pollImageUrl, Integer readCount) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.creatorName = creatorName;
        this.responseType = responseType;
        this.isBallot = isBallot;
        this.endedAt = endedAt;
        this.pollImageUrl = pollImageUrl;
        this.readCount = readCount;
    }

    public void addOption(Option option) {
        this.options.add(option);
        option.setPoll(this);
    }

    public void vote(Long optionId, Voter voter) {
        Option option = findOptionByOptionId(optionId);
        voter.vote(option);
    }

    public Option findOptionByOptionId(Long optionId) {
        return this.options.stream()
                .filter(el -> el.getId().equals(optionId))
                .findFirst()
                .orElseThrow(() -> new GollabaException(GollabaErrorCode.NOT_EXIST_OPTION));
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

    public void updatePollImageUrl(String pollImageUrl) {
        this.pollImageUrl = pollImageUrl;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }
}
