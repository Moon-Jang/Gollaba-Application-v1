package kr.mj.gollaba.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import kr.mj.gollaba.common.BaseApiResponse;
import kr.mj.gollaba.user.entity.User;
import kr.mj.gollaba.user.type.UserRoleType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
public class FindUserResponse implements BaseApiResponse {

    @ApiModelProperty(position = 0, example = "1")
    private Long userId;

    @ApiModelProperty(position = 1, example = "testId1234")
    private String uniqueId;

    @ApiModelProperty(position = 2, example = "홍길동")
    private String nickName;

    @ApiModelProperty(position = 3, example = "이미지 URL")
    private String profileImageUrl;

    @ApiModelProperty(position = 4, example = "이미지 URL")
    private String backgroundImageUrl;

    @ApiModelProperty(position = 5, example = "ROLE_USER")
    private UserRoleType userRole;

    @ApiModelProperty(position = 6)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @ApiModelProperty(position = 7)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    @Builder
    private FindUserResponse(Long userId, String uniqueId, String nickName, String profileImageUrl, String backgroundImageUrl, UserRoleType userRole, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.uniqueId = uniqueId;
        this.nickName = nickName;
        this.profileImageUrl = profileImageUrl;
        this.backgroundImageUrl = backgroundImageUrl;
        this.userRole = userRole;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static FindUserResponse create(User user) {
        return FindUserResponse.builder()
                .userId(user.getId())
                .uniqueId(user.getUniqueId())
                .nickName(user.getNickName())
                .profileImageUrl(user.getProfileImageUrl())
                .backgroundImageUrl(user.getBackgroundImageUrl())
                .userRole(user.getUserRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
