package kr.mj.gollaba.favorites.service;

import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import kr.mj.gollaba.favorites.dto.CreateFavoritesRequest;
import kr.mj.gollaba.favorites.dto.CreateFavoritesResponse;
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

    public CreateFavoritesResponse create(CreateFavoritesRequest request, User user) {
        Poll poll = pollRepository.findById(request.getPollId())
                .orElseThrow(() -> new GollabaException(GollabaErrorCode.NOT_EXIST_POLL));

        Favorites favorites = favoritesRepository.save(Favorites.of(user, poll));

        return new CreateFavoritesResponse(favorites.getId());
    }

    public void delete(Long favoritesId, User user) {
        Favorites favorites = favoritesRepository.findById(favoritesId)
                .orElseThrow(() -> new GollabaException(GollabaErrorCode.NOT_EXIST_FAVORITES));

        if (favorites.getUser().equals(user) == false) {
            throw new GollabaException(GollabaErrorCode.NOT_MATCHED_USER_FOR_FAVORITES);
        }

        favoritesRepository.deleteById(favorites.getId());
    }
}
