package kr.mj.gollaba.user.service;

import kr.mj.gollaba.auth.entity.UserProvider;
import kr.mj.gollaba.auth.repository.UserProviderRepository;
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
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final UserProviderRepository userProviderRepository;
	private final PasswordEncoder passwordEncoder;
	private final S3UploadService s3UploadService;
	public static final String PROFILE_IMAGE_PATH = "profile_image";
	public static final String BACKGROUND_IMAGE_PATH = "background_image";

	@Transactional
	public SignupResponse create(SignupRequest request) {
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new GollabaException(GollabaErrorCode.ALREADY_EXIST_USER);
		}

		User user = request.toUserEntity();
		User saveUser = userRepository.save(user);

		if (request.getProfileImage() != null) {
			String imageUrl = uploadPollImage(saveUser.getId(), request.getProfileImage());
			saveUser.updateProfileImageUrl(imageUrl);
			userRepository.save(saveUser);
		}

		if (request.getProfileImageUrl() != null) {
			saveUser.updateProfileImageUrl(request.getProfileImageUrl());
			userRepository.save(saveUser);
		}

		if (userProviderRepository.existsByProviderId(request.getProviderId())) {
			throw new GollabaException(GollabaErrorCode.ALREADY_EXIST_PROVIDER_ID);
		}

		UserProvider userProvider = request.toUserProviderEntity(user);

		userProviderRepository.save(userProvider);

		return SignupResponse.builder()
				.userId(saveUser.getId())
				.build();
	}

	public void updateNickName(UpdateUserRequest request, User user) {
		if (StringUtils.hasText(request.getNickName()) == false) {
			throw new GollabaException(GollabaErrorCode.INVALID_PARAMS, "닉네임을 입력해주세요.");
		}

		user.updateNickName(request.getNickName());
		userRepository.save(user);
	}

	public void updatePassword(UpdateUserRequest request, User user) {
		if (passwordEncoder.matches(request.getCurrentPassword(), user.getPassword()) == false) {
			throw new GollabaException(GollabaErrorCode.NOT_MATCHED_PASSWORD);
		}

		String newPassword = passwordEncoder.encode(request.getNewPassword());
		user.updatePassword(newPassword);
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

	private String uploadPollImage(long pollId, MultipartFile pollImage) {
		String fileName = s3UploadService.generateFileName(pollId, pollImage.getContentType());
		String imageUrl = s3UploadService.upload(PROFILE_IMAGE_PATH, fileName, pollImage);
		return imageUrl;
	}
}
