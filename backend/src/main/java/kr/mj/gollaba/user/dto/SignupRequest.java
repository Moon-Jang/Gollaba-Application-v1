package kr.mj.gollaba.user.dto;

import io.swagger.annotations.ApiModelProperty;
import kr.mj.gollaba.auth.entity.UserProvider;
import kr.mj.gollaba.auth.types.ProviderType;
import kr.mj.gollaba.common.BaseApiRequest;
import kr.mj.gollaba.common.util.ImageFileUtils;
import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import kr.mj.gollaba.user.entity.User;
import kr.mj.gollaba.user.type.UserRoleType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static kr.mj.gollaba.common.Const.MAX_IMAGE_UPLOAD_SIZE;

@Getter
@Setter
public class SignupRequest implements BaseApiRequest {

    @NotBlank
    @Size(min = 12, max = 100)
    @ApiModelProperty(position = 1, example = "testid123456@test.com", required = true)
    private String email;

    @Size(min = 10)
    private String providerId;

    @NotNull
    @ApiModelProperty(position = 2, example = "NAVER", required = true)
    private ProviderType providerType;

    @NotBlank
    @Size(min = 2, max = 20)
    @ApiModelProperty(position = 3, example = "홍길동", required = true)
    private String nickName;

    private MultipartFile profileImage;

    @Size(min = 20)
    @ApiModelProperty(position = 4, example = "프로필 이미지 URL")
    private String profileImageUrl;

    public UserProvider toUserProviderEntity(User user) {
        return UserProvider.builder()
                .providerId(providerId)
                .providerType(providerType)
                .user(user)
                .build();
    }

    public User toUserEntity() {
        return User.builder()
                .email(email)
                .nickName(nickName)
                .userRole(UserRoleType.ROLE_USER)
                .build();
    }

    @Override
    public void validate() {
        if (profileImage != null && profileImageUrl != null) {
            throw new GollabaException(GollabaErrorCode.INVALID_PARAMS, "profileImage 와 profileImageUrl 둘 중 하나만 입력해주세요.");
        }

        if (profileImage != null) {
            if (ImageFileUtils.isImageFile(profileImage) == false) {
                throw new GollabaException(GollabaErrorCode.INVALID_PARAMS, "이미지 파일이 아닙니다.");
            }

            if (profileImage.getSize() > MAX_IMAGE_UPLOAD_SIZE) {
                throw new GollabaException(GollabaErrorCode.INVALID_PARAMS, "이미지 용량은 5MB를 넘을 수 없습니다.");
            }
        }

    }

}
