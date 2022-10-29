package kr.mj.gollaba.user.dto;

import io.swagger.annotations.ApiModelProperty;
import kr.mj.gollaba.common.BaseApiRequest;
import kr.mj.gollaba.common.util.ImageFileUtils;
import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import kr.mj.gollaba.user.entity.User;
import kr.mj.gollaba.user.type.UserRoleType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static kr.mj.gollaba.common.Const.MAX_IMAGE_UPLOAD_SIZE;

@Getter
@Setter
public class SignupRequest implements BaseApiRequest {

    @NotBlank
    @Size(min = 12, max = 100)
    @ApiModelProperty(position = 1, example = "testid123456@test.com", required = true)
    private String email;

    @NotBlank
    @Size(min = 2, max = 20)
    @ApiModelProperty(position = 2, example = "홍길동", required = true)
    private String nickName;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "비밀번호 형식에 맞지 않습니다. 최소 8글자 최대 24글자 , 숫자 + 영어 + 특수문자 필수")
    @Size(min = 8, max = 24)
    @ApiModelProperty(position = 3, example = "test12345*", required = true)
    private String password;

    private MultipartFile profileImage;

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .nickName(nickName)
                .password(passwordEncoder.encode(password))
                .userRole(UserRoleType.ROLE_USER)
                .build();
    }

    @Override
    public void validate() {
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
