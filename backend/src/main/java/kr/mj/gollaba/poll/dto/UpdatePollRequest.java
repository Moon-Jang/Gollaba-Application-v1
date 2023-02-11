package kr.mj.gollaba.poll.dto;

import io.swagger.annotations.ApiModelProperty;
import kr.mj.gollaba.common.BaseApiRequest;
import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import kr.mj.gollaba.poll.entity.Option;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

import static kr.mj.gollaba.exception.GollabaErrorCode.INVALID_PARAMS;

@Getter
@Setter
public class UpdatePollRequest implements BaseApiRequest {

    @ApiModelProperty(example = "부먹 찍먹")
    @Size(min = 4, max = 50)
    private String title;

    @ApiModelProperty(example = "imageFile", hidden = true)
    private MultipartFile pollImage;

    @ApiModelProperty(required = false)
    private List<OptionDto> options;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endedAt;

    @Override
    public void validate() {
        LocalDateTime now = LocalDateTime.now();

        if (isEmptyPayload()) {
            throw new GollabaException(INVALID_PARAMS, "수정사항을 입력해주세요.");
        }

        if (endedAt != null && endedAt.isBefore(now.plusMinutes(30L))) {
            throw new GollabaException(GollabaErrorCode.INVALID_PARAMS, "투표 유효기간을 현재 시간보다 30분 미만으로 설정할 수 없습니다.");
        }
    }

    private boolean isEmptyPayload() {
        if (title == null && pollImage == null
                && options == null && endedAt == null) {
            return true;
        }

        return false;
    }

    @Getter
    @Setter
    public static class OptionDto {

        @ApiModelProperty(example = "탕수육", required = false)
        @NotBlank
        @Size(min = 2, max = 50)
        private String description;

        public Option toEntity() {
            return Option.builder()
                    .description(description)
                    .build();
        }
    }
}

