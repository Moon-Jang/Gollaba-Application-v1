package kr.mj.gollaba.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import kr.mj.gollaba.common.BaseApiRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginRequest implements BaseApiRequest {

    @NotBlank
    @Size(min = 12, max = 100)
    @ApiModelProperty(position = 1, example = "test@test.com", required = true)
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "비밀번호 형식에 맞지 않습니다.")
    @ApiModelProperty(position = 3, example = "test1234*", required = true)
    private String password;

    @Override
    public void validate() {

    }

}
