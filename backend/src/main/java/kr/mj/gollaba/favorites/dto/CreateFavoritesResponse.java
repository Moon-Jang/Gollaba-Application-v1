package kr.mj.gollaba.favorites.dto;

import io.swagger.annotations.ApiModelProperty;
import kr.mj.gollaba.common.BaseApiResponse;
import lombok.Getter;

@Getter
public class CreateFavoritesResponse implements BaseApiResponse {

    @ApiModelProperty(example = "1")
    private Long favoritesId;

    public CreateFavoritesResponse(Long favoritesId) {
        this.favoritesId = favoritesId;
    }
}
