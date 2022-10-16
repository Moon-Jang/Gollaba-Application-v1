package kr.mj.gollaba.unit.favorites.factory;

import kr.mj.gollaba.favorites.entity.Favorites;
import kr.mj.gollaba.poll.entity.Option;
import kr.mj.gollaba.poll.entity.Poll;
import kr.mj.gollaba.poll.entity.Voter;
import kr.mj.gollaba.unit.poll.factory.OptionFactory;
import kr.mj.gollaba.unit.poll.factory.PollFactory;
import kr.mj.gollaba.unit.user.factory.UserFactory;
import kr.mj.gollaba.user.entity.User;

import java.util.List;

public class FavoritesFactory {

    public static final long FAVORITES_TEST_ID = 1L;

    public static Favorites create() {
        User user = UserFactory.createWithId();
        List<Option> options = OptionFactory.createListWithId();
        Poll poll = PollFactory.createWithId(user, options);
        return Favorites.of(user, poll);
    }

    public static Favorites create(User user, Poll poll) {
        return Favorites.of(user, poll);
    }

    public static Favorites createWithId(User user, Poll poll) {
        return Favorites.builder()
                .id(FAVORITES_TEST_ID)
                .user(user)
                .poll(poll)
                .build();
    }

}
