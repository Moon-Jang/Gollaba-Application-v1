package kr.mj.gollaba.unit.favorites.service;

import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import kr.mj.gollaba.favorites.entity.Favorites;
import kr.mj.gollaba.favorites.repository.FavoritesRepository;
import kr.mj.gollaba.favorites.service.FavoritesService;
import kr.mj.gollaba.poll.entity.Option;
import kr.mj.gollaba.poll.entity.Poll;
import kr.mj.gollaba.poll.repository.PollRepository;
import kr.mj.gollaba.unit.common.ServiceTest;
import kr.mj.gollaba.unit.favorites.factory.FavoritesFactory;
import kr.mj.gollaba.unit.poll.factory.OptionFactory;
import kr.mj.gollaba.unit.poll.factory.PollFactory;
import kr.mj.gollaba.unit.user.factory.UserFactory;
import kr.mj.gollaba.user.entity.User;
import kr.mj.gollaba.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class FavoritesServiceTest extends ServiceTest {

    @InjectMocks
    FavoritesService favoritesService;

    @Mock
    private FavoritesRepository favoritesRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PollRepository pollRepository;

    @DisplayName("delete 는")
    @Nested
    class delete {

        @DisplayName("요청 유저와 즐겨찾기한 유저가 다르면")
        @Nested
        class not_matched_favorites_user_and_input_user {

            @DisplayName("예외를 발생한다.")
            @Test
            void throw_exception() {
                //given
                User user = UserFactory.createWithId();
                List<Option> options = OptionFactory.createListWithId();
                Poll poll = PollFactory.createWithId(user, options);
                Favorites favorites = FavoritesFactory.createWithId(user, poll);
                User otherUser = UserFactory.createWithId(999L);

                given(favoritesRepository.findById(anyLong()))
                        .willReturn(Optional.of(favorites));


                //when then
                assertThatThrownBy(() -> favoritesService.delete(favorites.getId(), otherUser))
                        .hasMessage(GollabaErrorCode.NOT_MATCHED_USER_FOR_FAVORITES.getDescription())
                        .isInstanceOf(GollabaException.class);

                verify(favoritesRepository, times(1)).findById(eq(favorites.getId()));
            }
        }
    }

}
