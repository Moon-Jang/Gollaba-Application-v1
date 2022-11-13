package kr.mj.gollaba.integration.user;

import kr.mj.gollaba.auth.repository.UserProviderRepository;
import kr.mj.gollaba.common.Const;
import kr.mj.gollaba.common.util.MultiValueMapGenerator;
import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import kr.mj.gollaba.integration.common.IntegrationTest;
import kr.mj.gollaba.unit.user.factory.UserProviderFactory;
import kr.mj.gollaba.user.dto.SignupRequest;
import kr.mj.gollaba.user.dto.UpdateUserRequest;
import kr.mj.gollaba.user.entity.User;
import kr.mj.gollaba.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static kr.mj.gollaba.unit.user.factory.UserFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends IntegrationTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserProviderRepository userProviderRepository;

	@DisplayName("회원 가입")
	@Test
	public void signup() throws Exception {
		//given
		SignupRequest request = new SignupRequest();
		request.setEmail(TEST_EMAIL);
		request.setNickName(TEST_NICK_NAME);
		request.setProviderId(UserProviderFactory.TEST_PROVIDER_ID);
		request.setProviderType(UserProviderFactory.TEST_PROVIDER_TYPE);
		File file = ResourceUtils.getFile("classpath:test_image.jpeg");
		InputStream inputStream = new FileInputStream(file);
		MockMultipartFile profileImage = new MockMultipartFile(
				"profileImage",
				"test.png",
				"image/png",
				inputStream);
		MultiValueMap<String, String> params = MultiValueMapGenerator.generate(request);

		//when
		ResultActions resultActions = mvc.perform(multipart(Const.ROOT_URL + "/signup")
				.file(profileImage)
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.params(params)
				.accept(MediaType.APPLICATION_JSON));

		//then
		resultActions
				.andDo(print())
				.andExpect(status().isCreated());

		User user = userRepository.findByEmail(TEST_EMAIL)
						.orElseThrow(() -> new GollabaException(GollabaErrorCode.NOT_EXIST_USER));
		assertThat(user.getNickName()).isEqualTo(TEST_NICK_NAME);
		assertThat(user.getProfileImageUrl()).isNotNull();
	}


	@DisplayName("닉네임 변경")
	@WithUserDetails(value = TEST_EXIST_EMAIL)
	@Test
	public void update_nickname() throws Exception {
		//given
		String testNickName = TEST_NICK_NAME + "님";
		UpdateUserRequest request = new UpdateUserRequest();
		request.setUpdateType(UpdateUserRequest.UpdateType.NICKNAME);
		request.setNickName(testNickName);

		//when
		ResultActions resultActions = mvc.perform(multipart(Const.ROOT_URL + "/users/update")
				.contentType(MediaType.MULTIPART_FORM_DATA)
						.params(MultiValueMapGenerator.generate(request))
				.accept(MediaType.APPLICATION_JSON));

		//then
		resultActions
				.andDo(print())
				.andExpect(status().isOk());
	}
}
