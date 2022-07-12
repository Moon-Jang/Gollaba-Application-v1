package kr.mj.gollaba.security.dto;

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
    @Size(min = 8, max = 32)
    @ApiModelProperty(position = 1, example = "testid123456", required = true)
    private String id;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "비밀번호 형식에 맞지 않습니다.")
    @ApiModelProperty(position = 3, example = "test12345*", required = true)
    private String password;

    @Override
    public void validate() {

    }

}
