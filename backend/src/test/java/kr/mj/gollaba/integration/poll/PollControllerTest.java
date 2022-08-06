package kr.mj.gollaba.integration.poll;

import kr.mj.gollaba.common.Const;
import kr.mj.gollaba.common.util.QueryStringGenerator;
import kr.mj.gollaba.integration.common.IntegrationTest;
import kr.mj.gollaba.poll.dto.CreatePollRequest;
import kr.mj.gollaba.poll.dto.FindAllPollRequest;
import kr.mj.gollaba.poll.type.PollingResponseType;
import kr.mj.gollaba.unit.poll.factory.PollFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PollControllerTest extends IntegrationTest {

    @DisplayName("투표 생성")
    @Test
    public void create_poll() throws Exception {
        //given
        CreatePollRequest request = generateCreateRequest();

        //when
        ResultActions resultActions = mvc.perform(post(Const.ROOT_URL + "/polls")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isCreated());
    }

    @DisplayName("투표 전체 조회")
    @Test
    public void findAll() throws Exception {
        //given
        FindAllPollRequest request = new FindAllPollRequest();
        request.setOffset(0);
        request.setLimit(15);

        String url = Const.ROOT_URL + "/polls" + QueryStringGenerator.generate(request);
        //when
        ResultActions resultActions = mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("totalCount").value(150))
                .andExpect(jsonPath("polls").isArray());
    }

    @DisplayName("투표 상세 조회")
    @Test
    public void find() throws Exception {
        //given

        String url = Const.ROOT_URL + "/polls/1";
        //when
        ResultActions resultActions = mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("pollId").isNumber());
    }

    private CreatePollRequest generateCreateRequest() {
        CreatePollRequest request = new CreatePollRequest();

        request.setTitle(PollFactory.TEST_TITLE);
        request.setCreatorName(PollFactory.TEST_CREATOR_NAME);
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
