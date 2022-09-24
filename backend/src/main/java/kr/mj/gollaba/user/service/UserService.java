package kr.mj.gollaba.user.service;

import kr.mj.gollaba.auth.PrincipalDetails;
import kr.mj.gollaba.common.service.S3UploadService;
import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import kr.mj.gollaba.user.dto.FindUserResponse;
import kr.mj.gollaba.user.dto.SignupRequest;
import kr.mj.gollaba.user.dto.SignupResponse;
import kr.mj.gollaba.user.dto.UpdateRequest;
import kr.mj.gollaba.user.entity.User;
import kr.mj.gollaba.user.repository.UserRepository;
import kr.mj.gollaba.user.type.UserRoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

import static kr.mj.gollaba.common.service.S3UploadService.BACKGROUND_IMAGE_PATH;
import static kr.mj.gollaba.common.service.S3UploadService.PROFILE_IMAGE_PATH;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final S3UploadService s3UploadService;

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

	public void updateNickName(UpdateRequest request, User user) {
		if (StringUtils.hasText(request.getNickName()) == false) {
			throw new GollabaException(GollabaErrorCode.INVALID_PARAMS, "닉네임을 입력해주세요.");
		}

		if (userRepository.existsByNickName(request.getNickName())) {
			throw new GollabaException(GollabaErrorCode.ALREADY_EXIST_NICKNAME);
		}

		user.updateNickName(request.getNickName());
		userRepository.save(user);
	}

	public void updatePassword(UpdateRequest request, User user) {
		if (passwordEncoder.matches(request.getCurrentPassword(), user.getPassword()) == false) {
			throw new GollabaException(GollabaErrorCode.NOT_MATCHED_PASSWORD);
		}

		user.updatePassword(user.getPassword());
		userRepository.save(user);
	}

	public void updateProfileImage(UpdateRequest request, User user) {
		String fileName = generateFileName(user.getId(), request.getProfileImage().getContentType());
		String imageUrl = s3UploadService.upload(PROFILE_IMAGE_PATH, fileName, request.getProfileImage());
		user.updateProfileImageUrl(imageUrl);
		userRepository.save(user);
	}

	public void updateBackgroundImage(UpdateRequest request, User user) {
		String fileName = generateFileName(user.getId(), request.getBackgroundImage().getContentType());
		String imageUrl = s3UploadService.upload(BACKGROUND_IMAGE_PATH, fileName, request.getBackgroundImage());
		user.updateBackgroundImageUrl(imageUrl);
		userRepository.save(user);
	}

	private String generateFileName(long id, String contentType) {
		return id + "_" + UUID.randomUUID() + "." + contentType.replace("image/", "");
	}

	public FindUserResponse find(Long userId, User user) {
		if (user.getUserRole() != UserRoleType.ROLE_ADMIN && user.getId().equals(userId) == false) {
			throw new GollabaException(GollabaErrorCode.FORBIDDEN);
		}

		return FindUserResponse.create(user);
	}

}
