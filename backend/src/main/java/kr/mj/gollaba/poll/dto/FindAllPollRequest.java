package kr.mj.gollaba.poll.dto;

import io.swagger.annotations.ApiModelProperty;
import kr.mj.gollaba.common.BaseApiRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
public class FindAllPollRequest implements BaseApiRequest {

    @ApiModelProperty(value = "userId // 회원일 경우", example = "1")
    @Min(0)
    private Long userId;

    @ApiModelProperty(value = "제목", example = "제목")
    private String title;

    @ApiModelProperty(example = "0", required = true)
    @NotNull
    @Min(0)
    private Integer offset;

    @ApiModelProperty(example = "15", required = true)
    @NotNull
    @Positive
    private Integer limit;


    public PollQueryFilter toFilter() {
        return PollQueryFilter.builder()
                .userId(userId)
                .title(title)
                .offset(offset)
                .limit(limit)
                .build();
    }

    @Override
    public void validate() {

    }

}
