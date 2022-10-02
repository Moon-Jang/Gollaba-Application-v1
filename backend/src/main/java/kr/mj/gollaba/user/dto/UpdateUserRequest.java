package kr.mj.gollaba.user.dto;

import io.swagger.annotations.ApiModelProperty;
import kr.mj.gollaba.common.BaseApiRequest;
import kr.mj.gollaba.common.util.ImageFileUtils;
import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UpdateUserRequest implements BaseApiRequest {

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
	private String currentPassword;

	@ApiModelProperty(position = 4, example = "newTest12345*")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
			message = "비밀번호 형식에 맞지 않습니다. 최소 8글자 최대 24글자 , 숫자 + 영어 + 특수문자 필수")
	@Size(min = 8, max = 24)
	private String newPassword;

	private MultipartFile profileImage;

	private MultipartFile backgroundImage;

	private static final long MAX_UPLOAD_SIZE = 1024 * 1024 * 5L;
	@Override
	public void validate() {
		switch (updateType) {
			case NICKNAME:
				if (StringUtils.hasText(nickName) == false) {
					throw new GollabaException(GollabaErrorCode.INVALID_PARAMS, "이름을 입력해주세요.");
				}
				break;
			case PASSWORD:
				if (StringUtils.hasText(currentPassword) == false) {
					throw new GollabaException(GollabaErrorCode.INVALID_PARAMS, "현재 비밀번호를 입력해주세요.");
				}

				if (StringUtils.hasText(newPassword) == false) {
					throw new GollabaException(GollabaErrorCode.INVALID_PARAMS, "새로운 비밀번호를 입력해주세요.");
				}
				break;
			case PROFILE_IMAGE:
				if (profileImage == null) {
					throw new GollabaException(GollabaErrorCode.INVALID_PARAMS, "프로필 사진 파일을 올려주세요.");
				}

				if (ImageFileUtils.isImageFile(profileImage) == false) {
					throw new GollabaException(GollabaErrorCode.INVALID_PARAMS, "이미지 파일이 아닙니다.");
				}

				if (profileImage.getSize() > MAX_UPLOAD_SIZE) {
					throw new GollabaException(GollabaErrorCode.INVALID_PARAMS, "이미지 용량은 5MB를 넘을 수 없습니다.");
				}
				break;
			case BACKGROUND_IMAGE:
				if (backgroundImage == null) {
					throw new GollabaException(GollabaErrorCode.INVALID_PARAMS, "배경 사진 파일을 올려주세요.");
				}

				if (ImageFileUtils.isImageFile(backgroundImage) == false) {
					throw new GollabaException(GollabaErrorCode.INVALID_PARAMS, "이미지 파일이 아닙니다.");
				}

				if (backgroundImage.getSize() > MAX_UPLOAD_SIZE) {
					throw new GollabaException(GollabaErrorCode.INVALID_PARAMS, "이미지 용량은 5MB를 넘을 수 없습니다.");
				}
				break;
			default:
				throw new GollabaException(GollabaErrorCode.INVALID_PARAMS, "존재하지 않는 UpdateType 입니다.");
		}
	}

	public enum UpdateType {
		NICKNAME,
		PASSWORD,
		PROFILE_IMAGE,
		BACKGROUND_IMAGE
	}
}
