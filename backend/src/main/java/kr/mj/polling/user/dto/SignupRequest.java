package kr.mj.polling.user.dto;

import io.swagger.annotations.ApiModelProperty;
import kr.mj.polling.user.entity.User;
import kr.mj.polling.user.type.UserRoleType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignupRequest {

    @NotBlank
    @Size(min = 8, max = 32)
    @ApiModelProperty(position = 1, example = "testId", required = true)
    private String uniqueId;

    @NotBlank
    @Size(min = 2, max = 20)
    @ApiModelProperty(position = 2, example = "홍길동", required = true)
    private String nickName;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "비밀번호 형식에 맞지 않습니다. 최소 8글자 최대 24글자 , 숫자 + 영어 + 특수문자 필수")
    @ApiModelProperty(position = 3, example = "test12345*", required = true)
    private String password;

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .uniqueId(uniqueId)
                .nickName(nickName)
                .password(passwordEncoder.encode(password))
                .userRole(UserRoleType.ROLE_USER)
                .build();
    }

}
