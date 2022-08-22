package kr.mj.gollaba.poll.dto;

import io.swagger.annotations.ApiModelProperty;
import kr.mj.gollaba.common.BaseApiRequest;
import kr.mj.gollaba.common.util.CryptUtils;
import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import kr.mj.gollaba.poll.entity.Poll;
import kr.mj.gollaba.poll.type.PollingResponseType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class VoteRequest implements BaseApiRequest {

    private final static String ANONYMOUS_NAME = "익명";

    @NotNull
    @Positive
    private Long pollId;

    @NotNull
    private List<Long> optionIds;

    @Positive
    private Long userId;

    @Size(min = 2, max = 20)
    private String voterName;

    @ApiModelProperty(hidden = true)
    private String ipAddress;

    @Override
    public void validate() {
        if (!StringUtils.hasText(ipAddress)) {
            throw new GollabaException(GollabaErrorCode.INVALID_IP_ADDRESS);
        }
    }

    public String generateVoterName(Poll poll) {
        int count = (int) poll.getOptions().stream()
                .flatMap(el -> el.getVoters().stream())
                .count();

        return ANONYMOUS_NAME + count + 1;
    }
}
