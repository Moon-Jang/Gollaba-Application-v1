package kr.mj.gollaba.poll.entity;

import kr.mj.gollaba.common.entity.BaseTimeEntity;
import kr.mj.gollaba.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Voter extends BaseTimeEntity {

    @Id
    @Column(name = "voter_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_option_id" , nullable = false)
    private Option option;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , nullable = true)
    private User user;

    @Column(name = "voter_name", nullable = false)
    private String voterName;

    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

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

    public void registerUser(User user) {
        this.user = user;
    }

}
