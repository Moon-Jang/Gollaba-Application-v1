package kr.mj.gollaba.user.service;

import kr.mj.gollaba.common.service.S3UploadService;
import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import kr.mj.gollaba.user.dto.FindUserResponse;
import kr.mj.gollaba.user.dto.SignupRequest;
import kr.mj.gollaba.user.dto.SignupResponse;
import kr.mj.gollaba.user.dto.UpdateUserRequest;
import kr.mj.gollaba.user.entity.User;
import kr.mj.gollaba.user.repository.UserRepository;
import kr.mj.gollaba.user.type.UserRoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final S3UploadService s3UploadService;
	public static final String PROFILE_IMAGE_PATH = "profile_image";
	public static final String BACKGROUND_IMAGE_PATH = "background_image";

	public SignupResponse create(SignupRequest request) {
		if (userRepository.existsByUniqueId(request.getId())) {
			throw new GollabaException(GollabaErrorCode.ALREADY_EXIST_USER);
		}

		if (userRepository.existsByNickName(request.getNickName())) {
			throw new GollabaException(GollabaErrorCode.ALREADY_EXIST_USER);
		}

		User user = request.toEntity(passwordEncoder);
		User saveResult = userRepository.save(user);

		return SignupResponse.builder()
				.userId(saveResult.getId())
				.build();
	}

	public void updateNickName(UpdateUserRequest request, User user) {
		if (StringUtils.hasText(request.getNickName()) == false) {
			throw new GollabaException(GollabaErrorCode.INVALID_PARAMS, "닉네임을 입력해주세요.");
		}

		if (userRepository.existsByNickName(request.getNickName())) {
			throw new GollabaException(GollabaErrorCode.ALREADY_EXIST_NICKNAME);
		}

		user.updateNickName(request.getNickName());
		userRepository.save(user);
	}

	public void updatePassword(UpdateUserRequest request, User user) {
		if (passwordEncoder.matches(request.getCurrentPassword(), user.getPassword()) == false) {
			throw new GollabaException(GollabaErrorCode.NOT_MATCHED_PASSWORD);
		}

		user.updatePassword(user.getPassword());
		userRepository.save(user);
	}

	public void updateProfileImage(UpdateUserRequest request, User user) {
		String fileName = s3UploadService.generateFileName(user.getId(), request.getProfileImage().getContentType());
		String imageUrl = s3UploadService.upload(PROFILE_IMAGE_PATH, fileName, request.getProfileImage());
		user.updateProfileImageUrl(imageUrl);
		userRepository.save(user);
	}

	public void updateBackgroundImage(UpdateUserRequest request, User user) {
		String fileName = s3UploadService.generateFileName(user.getId(), request.getBackgroundImage().getContentType());
		String imageUrl = s3UploadService.upload(BACKGROUND_IMAGE_PATH, fileName, request.getBackgroundImage());
		user.updateBackgroundImageUrl(imageUrl);
		userRepository.save(user);
	}

	public FindUserResponse find(Long userId, User user) {
		if (user.getUserRole() != UserRoleType.ROLE_ADMIN && user.getId().equals(userId) == false) {
			throw new GollabaException(GollabaErrorCode.FORBIDDEN);
		}

		return FindUserResponse.create(user);
	}

}
