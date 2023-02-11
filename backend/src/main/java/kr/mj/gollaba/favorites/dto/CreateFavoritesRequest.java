package kr.mj.gollaba.favorites.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import kr.mj.gollaba.common.serializer.HashIdDeSerializer;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateFavoritesRequest {

    @NotNull
    @JsonDeserialize(using = HashIdDeSerializer.class)
    @ApiModelProperty(dataType = "string", example = "hashId", required = true)
    private Long pollId;

}
