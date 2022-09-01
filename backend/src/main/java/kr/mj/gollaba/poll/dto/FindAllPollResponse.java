package kr.mj.gollaba.poll.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import kr.mj.gollaba.common.BaseApiRequest;
import kr.mj.gollaba.common.BaseApiResponse;
import kr.mj.gollaba.poll.entity.Option;
import kr.mj.gollaba.poll.entity.Poll;
import kr.mj.gollaba.poll.entity.Voter;
import kr.mj.gollaba.poll.type.PollingResponseType;
import kr.mj.gollaba.user.entity.User;
import kr.mj.gollaba.user.type.UserRoleType;
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

	private long totalCount;

	private List<PollResponse> polls = new ArrayList<>();

	public FindAllPollResponse(long totalCount, List<Poll> polls) {
		this.totalCount = totalCount;
		this.polls = polls.stream()
				.map(el -> PollResponse.builder()
						.pollId(el.getId())
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
										.voteCount(option.getVoters().size())
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

		private String title;

		private String creatorName;

		private PollingResponseType responseType;

		private Boolean isBallot;

		private LocalDateTime endedAt;

		private Long totalVoteCount;

		private List<OptionResponse> options = new ArrayList<>();

		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		private LocalDateTime createdAt;

		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		private LocalDateTime updatedAt;

		@Builder
		private PollResponse(Long pollId, String title, String creatorName, PollingResponseType responseType, Boolean isBallot, LocalDateTime endedAt, List<OptionResponse> options, LocalDateTime createdAt, LocalDateTime updatedAt) {
			this.pollId = pollId;
			this.title = title;
			this.creatorName = creatorName;
			this.responseType = responseType;
			this.isBallot = isBallot;
			this.endedAt = endedAt;
			this.options = options;
			this.createdAt = createdAt;
			this.updatedAt = updatedAt;
			this.totalVoteCount = Long.valueOf(options
					.stream()
					.mapToInt(option -> option.getVoteCount())
					.sum());
		}

	}

	@Getter
	public static class OptionResponse {

		private Long optionId;

		private String description;

		private Integer voteCount;

		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		private LocalDateTime createdAt;

		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		private LocalDateTime updatedAt;

		@Builder
		public OptionResponse(Long optionId, String description, int voteCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
			this.optionId = optionId;
			this.description = description;
			this.voteCount = voteCount;
			this.createdAt = createdAt;
			this.updatedAt = updatedAt;
		}

	}

}
