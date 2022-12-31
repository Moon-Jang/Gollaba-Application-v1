package kr.mj.gollaba.poll.dto;

import io.swagger.annotations.ApiModelProperty;
import kr.mj.gollaba.common.BaseApiRequest;
import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class IncreaseReadCountRequest implements BaseApiRequest {

    private Long pollId;

    private String ipAddress;

    @Override
    public void validate() {
        if (pollId == null) {
            throw new GollabaException(GollabaErrorCode.INVALID_PARAMS, "pollId가 존재하지 않습니다.");
        }

//        if (!StringUtils.hasText(ipAddress)) {
//            throw new GollabaException(GollabaErrorCode.INVALID_IP_ADDRESS);
//        }
    }
}
