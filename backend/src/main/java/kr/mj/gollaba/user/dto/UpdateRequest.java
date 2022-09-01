package kr.mj.gollaba.user.dto;

import io.swagger.annotations.ApiModelProperty;
import kr.mj.gollaba.common.BaseApiRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UpdateRequest implements BaseApiRequest {

	@ApiModelProperty(position = 1, example = "NICKNAME", required = true)
	@NotNull
	private UpdateType updateType;

	@ApiModelProperty(position = 2, example = "홍길동")
	@Size(min = 2, max = 20)
	private String nickName;

	@ApiModelProperty(position = 3, example = "test12345*")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
			message = "비밀번호 형식에 맞지 않습니다. 최소 8글자 최대 24글자 , 숫자 + 영어 + 특수문자 필수")
	@Size(min = 8, max = 24)
	private String password;

	private MultipartFile profileImage;

	private MultipartFile backgroundImage;


	@Override
	public void validate() {

	}

	public enum UpdateType {
		NICKNAME,
		PASSWORD,
		PROFILE_IMAGE,
		BACKGROUND_IMAGE
	}
}
