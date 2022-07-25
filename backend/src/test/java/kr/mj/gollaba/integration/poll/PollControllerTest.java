package kr.mj.gollaba.integration.poll;

import kr.mj.gollaba.common.Const;
import kr.mj.gollaba.integration.common.IntegrationTest;
import kr.mj.gollaba.poll.dto.CreatePollRequest;
import kr.mj.gollaba.poll.type.PollingResponseType;
import kr.mj.gollaba.unit.poll.factory.PollingFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PollControllerTest extends IntegrationTest {

    @DisplayName("투표 생성")
    @Test
    public void create_poll() throws Exception {
        //given
        CreatePollRequest request = generateRequest();

        //when
        ResultActions resultActions = mvc.perform(post(Const.ROOT_URL + "/polls")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isCreated());
    }

    private CreatePollRequest generateRequest() {
        CreatePollRequest request = new CreatePollRequest();

        request.setTitle(PollingFactory.TEST_TITLE);
        request.setCreatorName(PollingFactory.TEST_CREATOR_NAME);
        request.setEndedAt(LocalDateTime.now().plusDays(3L));
        request.setResponseType(PollingResponseType.SINGLE);
        request.setIsBallot(false);

        List<CreatePollRequest.OptionDto> optionDtos = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            CreatePollRequest.OptionDto optionDto = new CreatePollRequest.OptionDto();
            optionDto.setDescription("항목" + i);

            optionDtos.add(optionDto);
        }

        request.setOptions(optionDtos);

        return request;
    }
}
