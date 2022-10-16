package kr.mj.gollaba.favorites.service;

import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import kr.mj.gollaba.favorites.dto.CreateFavoritesRequest;
import kr.mj.gollaba.favorites.entity.Favorites;
import kr.mj.gollaba.favorites.repository.FavoritesRepository;
import kr.mj.gollaba.poll.entity.Poll;
import kr.mj.gollaba.poll.repository.PollRepository;
import kr.mj.gollaba.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoritesService {

    private final FavoritesRepository favoritesRepository;
    private final PollRepository pollRepository;

    public void create(CreateFavoritesRequest request, User user) {
        Poll poll = pollRepository.findById(request.getPollId())
                .orElseThrow(() -> new GollabaException(GollabaErrorCode.NOT_EXIST_POLL));

        favoritesRepository.save(Favorites.of(user, poll));
    }

}
