package kr.mj.gollaba.integration.auth;

import kr.mj.gollaba.auth.dto.LoginRequest;
import kr.mj.gollaba.common.Const;
import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.integration.common.IntegrationTest;
import kr.mj.gollaba.unit.user.factory.UserFactory;
import kr.mj.gollaba.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends IntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @DisplayName("로그인 성공")
    @Test
    public void login_success() throws Exception {
        //given
        LoginRequest request = new LoginRequest();
        request.setId(UserFactory.TEST_EXIST_UNIQUE_ID);
        request.setPassword(UserFactory.TEST_PASSWORD);

        //when
        ResultActions resultActions = mvc.perform(post(Const.ROOT_URL + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isString())
                .andExpect(jsonPath("$.refreshToken").isString());
    }

    @DisplayName("로그인 실패")
    @Test
    public void login_fail() throws Exception {
        //given
        String failPassword = "failPassword";
        LoginRequest request = new LoginRequest();
        request.setId(UserFactory.TEST_UNIQUE_ID);
        request.setPassword(failPassword);

        //when
        ResultActions resultActions = mvc.perform(post(Const.ROOT_URL + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", GollabaErrorCode.FAIL_LOGIN.getCode()).exists());
    }

}
