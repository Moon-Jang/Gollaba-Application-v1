package kr.mj.gollaba.integration.user;

import kr.mj.gollaba.common.Const;
import kr.mj.gollaba.integration.common.IntegrationTest;
import kr.mj.gollaba.unit.user.factory.UserFactory;
import kr.mj.gollaba.user.dto.SignupRequest;
import kr.mj.gollaba.user.dto.UpdateRequest;
import kr.mj.gollaba.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends IntegrationTest {

	@Autowired
	private UserRepository userRepository;

	@DisplayName("회원 가입")
	@Test
	public void signup() throws Exception {
		//given
		SignupRequest request = new SignupRequest();
		request.setId(UserFactory.TEST_UNIQUE_ID);
		request.setNickName(UserFactory.TEST_NICK_NAME);
		request.setPassword(UserFactory.TEST_PASSWORD);

		//when
		ResultActions resultActions = mvc.perform(post(Const.ROOT_URL + "/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
				.accept(MediaType.APPLICATION_JSON));

		//then
		resultActions
				.andExpect(status().isCreated());
	}

	
	@DisplayName("닉네임 변경")
	@Test
	public void update_nickname() throws Exception {
		//given
		String testNickName = UserFactory.TEST_NICK_NAME + "님";
		UpdateRequest request = new UpdateRequest();
		request.setUpdateType(UpdateRequest.UpdateType.NICKNAME);
		request.setNickName(testNickName);

		//when
		ResultActions resultActions = mvc.perform(post(Const.ROOT_URL + "/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
				.accept(MediaType.APPLICATION_JSON));

		//then
		resultActions
				.andExpect(status().isCreated());
	}
}
