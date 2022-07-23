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

        int currentSequence = options.stream()
                .mapToInt(el -> el.sequence)
                .min()
                .getAsInt();
        final int optionDtosSize = options.size();
        
        for (int i = 0; i < optionDtosSize; i++) {
            OptionDto optionDto = options.get(i);

            if (optionDto.getSequence() != currentSequence++) {
                throw new GollabaException(GollabaErrorCode.INVALID_PARAMS, "항목 순서가 잘못되었습니다. 해당 오류 지속적으로 발생시 관리자에게 문의해주세요.");
            }
        }
    }

    public Poll toDto() {
        Poll poll = Poll.builder()
                .title(title)
                .creatorName(creatorName)
                .responseType(responseType)
                .isBallot(isBallot)
                .endedAt(endedAt)
                .build();

        options.stream()
                .map(el -> Option.builder()
                        .sequence(el.getSequence())
                        .description(el.getDescription())
                        .build())
                .forEach(el -> poll.addoption(el));

        return poll;
    }

    @Getter
    @Setter
    public static class OptionDto {

        @ApiModelProperty(example = "0", required = true)
        @Positive
        private int sequence;

        @ApiModelProperty(example = "탕수육", required = true)
        @NotBlank
        @Size(min = 4, max = 50)
        private String description;

    }

}
