package kr.mj.gollaba.poll.dto;

import io.swagger.annotations.ApiModelProperty;
import kr.mj.gollaba.common.BaseApiRequest;
import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import kr.mj.gollaba.poll.entity.Poll;
import kr.mj.gollaba.poll.entity.Option;
import kr.mj.gollaba.poll.type.PollingResponseType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CreatePollRequest implements BaseApiRequest {

    @ApiModelProperty(example = "1", required = true)
    private Long userId;

    @ApiModelProperty(example = "부먹 찍먹", required = true)
    @NotBlank
    @Size(min = 4, max = 50)
    private String title;

    @ApiModelProperty(example = "홍길동", required = true)
    @NotBlank
    @Size(min = 2, max = 50)
    private String creatorName;

    @ApiModelProperty(example = "SINGLE", required = true)
    private PollingResponseType responseType;

    @ApiModelProperty(example = "true", required = true)
    private Boolean isBallot;

    private LocalDateTime endedAt = LocalDateTime.now().plusDays(7L);

    private List<OptionDto> options = new ArrayList<>();

    @Override
    public void validate() {
        LocalDateTime now = LocalDateTime.now();

        if (options.size() < 2) {
            throw new GollabaException(GollabaErrorCode.INVALID_PARAMS, "투표 항목은 2개 이상이어야 합니다.");
        }

        if (now.plusMinutes(30L).isAfter(endedAt)) {
            throw new GollabaException(GollabaErrorCode.INVALID_PARAMS, "투표 유효기간을 30분 미만으로 설정할 수 없습니다.");
        }

    }

    public Poll toEntity() {
        Poll poll = Poll.builder()
                .title(title)
                .creatorName(creatorName)
                .responseType(responseType)
                .isBallot(isBallot)
                .endedAt(endedAt)
                .build();

        options.stream()
                .map(el -> Option.builder()
                        .description(el.getDescription())
                        .build())
                .forEach(el -> poll.addOption(el));

        return poll;
    }

    @Getter
    @Setter
    public static class OptionDto {

        @ApiModelProperty(example = "탕수육", required = true)
        @NotBlank
        @Size(min = 2, max = 50)
        private String description;

    }

}
