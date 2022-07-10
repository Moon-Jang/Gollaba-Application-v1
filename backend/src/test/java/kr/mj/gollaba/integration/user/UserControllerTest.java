package kr.mj.gollaba.integration.user;

import kr.mj.gollaba.common.Const;
import kr.mj.gollaba.integration.common.BaseIntegrationTest;
import kr.mj.gollaba.unit.user.factory.UserFactory;
import kr.mj.gollaba.user.dto.SignupRequest;
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
