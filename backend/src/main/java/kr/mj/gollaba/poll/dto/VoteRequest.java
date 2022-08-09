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

    }

    public void validate(Poll poll, CryptUtils cryptUtils) {
        if (!StringUtils.hasText(ipAddress)) {
            throw new GollabaException(GollabaErrorCode.INVALID_IP_ADDRESS);
        }

        if (poll.getResponseType() == PollingResponseType.SINGLE && this.getOptionIds().size() > 1) {
            throw new GollabaException(GollabaErrorCode.NOT_AVAILABLE_MULTI_VOTE_BY_RESPONSE_TYPE);
        }

        if (poll.getIsBallot() && voterName != null) {
            throw new GollabaException(GollabaErrorCode.DONT_NEED_VOTER_NAME);
        }

        final boolean isAlreadyVote = poll.getOptions()
                .stream()
                .flatMap(option -> option.getVoters().stream())
                .filter(voter -> optionIds.contains(voter.getOption().getId()))
                .map(voter -> cryptUtils.decrypt(voter.getIpAddress()))
                .anyMatch(address -> address.equals(this.ipAddress));

        if (isAlreadyVote) {
            throw new GollabaException(GollabaErrorCode.ALREADY_VOTE);
        }
    }

    public String generateVoterName(Poll poll) {
        int count = (int) poll.getOptions().stream()
                .flatMap(el -> el.getVoters().stream())
                .count();

        return ANONYMOUS_NAME + count + 1;
    }
}
