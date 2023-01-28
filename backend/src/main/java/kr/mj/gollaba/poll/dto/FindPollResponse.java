package kr.mj.gollaba.poll.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.mj.gollaba.common.BaseApiResponse;
import kr.mj.gollaba.common.serializer.HashIdSerializer;
import kr.mj.gollaba.poll.entity.Poll;
import kr.mj.gollaba.poll.type.PollingResponseType;
import kr.mj.gollaba.user.type.UserRoleType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class FindPollResponse implements BaseApiResponse {

	@JsonSerialize(using = HashIdSerializer.class)
	private Long pollId;

	private UserResponse user;

	private String title;

	private String creatorName;

	private PollingResponseType responseType;

	private Boolean isBallot;

	private LocalDateTime endedAt;

	private String pollImageUrl;

	private Long totalVoteCount;

	private List<OptionResponse> options = new ArrayList<>();

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDateTime createdAt;

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDateTime updatedAt;

	public FindPollResponse(Poll poll) {
		this.pollId = poll.getId();
		this.user = poll.getUser() == null
				? null
				: UserResponse.builder()
				.userId(poll.getUser().getId())
				.nickName(poll.getUser().getNickName())
				.email(poll.getUser().getEmail())
				.userRole(poll.getUser().getUserRole())
				.build();
		this.title = poll.getTitle();
		this.creatorName = poll.getCreatorName();
		this.responseType = poll.getResponseType();
		this.isBallot = poll.getIsBallot();
		this.endedAt = poll.getEndedAt();
		this.pollImageUrl = poll.getPollImageUrl();
		this.options = poll.getOptions()
				.stream()
				.map(option -> OptionResponse.builder()
						.optionId(option.getId())
						.description(option.getDescription())
						.imageUrl(option.getImageUrl())
						.voters(option.getVoters() == null
								? null
								: option.getVoters()
								.stream()
								.map(voter -> VoterResponse.builder()
										.voterId(voter.getId())
										.userId(voter.getUser() == null ? null : voter.getUser().getId())
										.ipAddress(voter.getIpAddress())
										.voterName(voter.getVoterName())
										.createdAt(voter.getCreatedAt())
										.updatedAt(voter.getUpdatedAt())
										.build())
								.collect(Collectors.toList()))
						.createdAt(option.getCreatedAt())
						.updatedAt(option.getUpdatedAt())
						.build())
				.collect(Collectors.toList());
		this.createdAt = poll.getCreatedAt();
		this.updatedAt = poll.getUpdatedAt();
		this.totalVoteCount = Long.valueOf(poll.getOptions()
				.stream()
				.mapToInt(option -> option.getVoters().size())
				.sum());
	}

	@Getter
	public static class OptionResponse {

		private Long optionId;

		private String description;

		private String imageUrl;

		private List<VoterResponse> voters = new ArrayList<>();

		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		private LocalDateTime createdAt;

		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		private LocalDateTime updatedAt;

		@Builder
		public OptionResponse(Long optionId, String description, String imageUrl, List<VoterResponse> voters, LocalDateTime createdAt, LocalDateTime updatedAt) {
			this.optionId = optionId;
			this.description = description;
			this.imageUrl = imageUrl;
			this.voters = voters;
			this.createdAt = createdAt;
			this.updatedAt = updatedAt;
		}
	}

	@Getter
	public static class UserResponse {

		private Long userId;

		private String email;

		private String nickName;

		private UserRoleType userRole;

		@Builder
		private UserResponse(Long userId, String email, String nickName, UserRoleType userRole) {
			this.userId = userId;
			this.email = email;
			this.nickName = nickName;
			this.userRole = userRole;
		}

	}

	@Getter
	public static class VoterResponse {

		private Long voterId;

		private Long userId;

		private String voterName;

		private String ipAddress;

		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		private LocalDateTime createdAt;

		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		private LocalDateTime updatedAt;

		@Builder
		private VoterResponse(Long voterId, Long userId, String voterName, String ipAddress, LocalDateTime createdAt, LocalDateTime updatedAt) {
			this.voterId = voterId;
			this.userId = userId;
			this.voterName = voterName;
			this.ipAddress = ipAddress;
			this.createdAt = createdAt;
			this.updatedAt = updatedAt;
		}
	}

}
