package kr.mj.gollaba.poll.dto;

import io.swagger.annotations.ApiModelProperty;
import kr.mj.gollaba.common.BaseApiRequest;
import kr.mj.gollaba.common.util.CryptUtils;
import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import kr.mj.gollaba.poll.entity.Voter;
import kr.mj.gollaba.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class VoteRequest implements BaseApiRequest {

    @NotNull
    @Positive
    private Long pollId;

    @NotNull
    @Positive
    private Long optionId;

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

    public Voter toEntity(CryptUtils cryptUtils) {
        return Voter.builder()
                .voterName(voterName)
                .ipAddress(cryptUtils.encrypt(ipAddress))
                .build();
    }

}
