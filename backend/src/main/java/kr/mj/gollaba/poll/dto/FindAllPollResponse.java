package kr.mj.gollaba.poll.dto;

import io.swagger.annotations.ApiModelProperty;
import kr.mj.gollaba.common.BaseApiRequest;
import kr.mj.gollaba.common.BaseApiResponse;
import kr.mj.gollaba.poll.entity.Option;
import kr.mj.gollaba.poll.entity.Poll;
import kr.mj.gollaba.poll.entity.Voter;
import kr.mj.gollaba.poll.type.PollingResponseType;
import kr.mj.gollaba.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class FindAllPollResponse implements BaseApiResponse {

    List<PollResponse> polls = new ArrayList<>();

    public FindAllPollResponse(List<Poll> polls) {
        this.polls = polls.stream()
                .map(el -> PollResponse.builder()
                        .pollId(el.getId())
                        .user(el.getUser())
                        .title(el.getTitle())
                        .creatorName(el.getCreatorName())
                        .responseType(el.getResponseType())
                        .isBallot(el.getIsBallot())
                        .endedAt(el.getEndedAt())
                        .options(el.getOptions()
                                .stream()
                                .map(option -> OptionResponse.builder()
                                        .optionId(option.getId())
                                        .description(option.getDescription())
                                        //.voters(option.getVoters())
                                        .createdAt(option.getCreatedAt())
                                        .updatedAt(option.getUpdatedAt())
                                        .build())
                                .collect(Collectors.toList()))
                        .createdAt(el.getCreatedAt())
                        .updatedAt(el.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Getter
    public static class PollResponse {

        private Long pollId;

        private User user;

        private String title;

        private String creatorName;

        private PollingResponseType responseType;

        private Boolean isBallot;

        private LocalDateTime endedAt;

        private List<OptionResponse> options = new ArrayList<>();

        private LocalDateTime createdAt;

        private LocalDateTime updatedAt;

        @Builder
        private PollResponse(Long pollId, User user, String title, String creatorName, PollingResponseType responseType, Boolean isBallot, LocalDateTime endedAt, List<OptionResponse> options, LocalDateTime createdAt, LocalDateTime updatedAt) {
            this.pollId = pollId;
            this.user = user;
            this.title = title;
            this.creatorName = creatorName;
            this.responseType = responseType;
            this.isBallot = isBallot;
            this.endedAt = endedAt;
            this.options = options;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }

    }

    @Getter
    public static class OptionResponse {

        private Long optionId;

        private String description;

        //@ApiModelProperty(hidden = true)
        //private List<Voter> voters = new ArrayList<>();

        private LocalDateTime createdAt;

        private LocalDateTime updatedAt;

        @Builder
        private OptionResponse(Long optionId, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
            this.optionId = optionId;
            this.description = description;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }

    }


}
