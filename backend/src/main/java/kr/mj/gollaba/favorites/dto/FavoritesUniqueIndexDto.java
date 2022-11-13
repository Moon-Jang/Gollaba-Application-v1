package kr.mj.gollaba.favorites.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FavoritesUniqueIndexDto {

    private final long pollId;

    private final long userId;

    @Builder
    private FavoritesUniqueIndexDto(long pollId, long userId) {
        this.pollId = pollId;
        this.userId = userId;
    }

    public static FavoritesUniqueIndexDto of(long pollId, long userId) {
        return builder()
                .userId(userId)
                .pollId(pollId)
                .build();
    }
}
