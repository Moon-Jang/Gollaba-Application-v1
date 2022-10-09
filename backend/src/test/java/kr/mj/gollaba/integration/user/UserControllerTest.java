package kr.mj.gollaba.integration.user;

import kr.mj.gollaba.common.Const;
import kr.mj.gollaba.common.util.MultiValueMapGenerator;
import kr.mj.gollaba.integration.common.IntegrationTest;
import kr.mj.gollaba.unit.user.factory.UserFactory;
import kr.mj.gollaba.user.dto.SignupRequest;
import kr.mj.gollaba.user.dto.UpdateUserRequest;
import kr.mj.gollaba.user.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends IntegrationTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@DisplayName("회원 가입")
	@Test
	public void signup() throws Exception {
		//given
		SignupRequest request = new SignupRequest();
		request.setId(UserFactory.TEST_UNIQUE_ID + "test");
		request.setNickName(UserFactory.TEST_NICK_NAME);
		request.setPassword(UserFactory.TEST_PASSWORD);

		//when
		ResultActions resultActions = mvc.perform(post(Const.ROOT_URL + "/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
				.accept(MediaType.APPLICATION_JSON));

		//then
		resultActions
				.andDo(print())
				.andExpect(status().isCreated());
	}


	@DisplayName("닉네임 변경")
	@WithUserDetails(value = UserFactory.TEST_EXIST_UNIQUE_ID)
	@Test
	public void update_nickname() throws Exception {
		//given
		String testNickName = UserFactory.TEST_NICK_NAME + "님";
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

	@Disabled
	@DisplayName("비밀번호 변경")
	@WithMockUser(username = UserFactory.TEST_UNIQUE_ID, roles = "ROLE_USER")
	@Test
	public void update_password() throws Exception {
		//given
		String testPassword = "testPass123456!@";
		UpdateUserRequest request = new UpdateUserRequest();
		request.setUpdateType(UpdateUserRequest.UpdateType.PASSWORD);
		request.setCurrentPassword(UserFactory.TEST_PASSWORD);
		request.setNewPassword(testPassword);

		boolean res = passwordEncoder.matches(UserFactory.TEST_PASSWORD, "$2a$10$tQFqs1ZnUn.InIntVZkFlOO2PzYVzKY0HPDH/812okQo4bDNCQYIy");
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
