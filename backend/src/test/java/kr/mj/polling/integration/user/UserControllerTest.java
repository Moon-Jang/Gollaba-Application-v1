package kr.mj.polling.integration.user;

import kr.mj.polling.common.Const;
import kr.mj.polling.integration.common.BaseIntegrationTest;
import kr.mj.polling.unit.user.factory.UserFactory;
import kr.mj.polling.user.dto.SignupRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseIntegrationTest {

    @DisplayName("회원 가입")
    @Test
    public void signupByAvailableUser() throws Exception {
        //given
        SignupRequest request = new SignupRequest();
        request.setUniqueId(UserFactory.TEST_UNIQUE_ID);
        request.setNickName(UserFactory.TEST_NICK_NAME);
        request.setPassword(UserFactory.TEST_PASSWORD);

        //when
        ResultActions resultActions = mvc.perform(post(Const.ROOT_URL + "/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").isNumber());
    }
}
