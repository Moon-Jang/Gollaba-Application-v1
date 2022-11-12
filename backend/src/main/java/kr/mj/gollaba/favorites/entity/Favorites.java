package kr.mj.gollaba.favorites.entity;

import kr.mj.gollaba.common.entity.BaseTimeEntity;
import kr.mj.gollaba.poll.entity.Poll;
import kr.mj.gollaba.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favorites extends BaseTimeEntity {

    @Id
    @Column(name = "favorites_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_id" , nullable = false)
    private Poll poll;

    @Builder
    private Favorites(Long id, User user, Poll poll) {
        this.id = id;
        this.user = user;
        this.poll = poll;
    }

    public static Favorites of(User user, Poll poll) {
        return builder()
                .user(user)
                .poll(poll)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favorites favorites = (Favorites) o;
        return id.equals(favorites.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
