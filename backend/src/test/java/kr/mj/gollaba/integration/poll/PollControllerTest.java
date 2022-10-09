package kr.mj.gollaba.integration.poll;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.mj.gollaba.common.Const;
import kr.mj.gollaba.common.util.MultiValueMapGenerator;
import kr.mj.gollaba.common.util.QueryStringGenerator;
import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import kr.mj.gollaba.integration.common.IntegrationTest;
import kr.mj.gollaba.poll.dto.CreatePollRequest;
import kr.mj.gollaba.poll.dto.FindAllPollRequest;
import kr.mj.gollaba.poll.dto.UpdatePollRequest;
import kr.mj.gollaba.poll.dto.VoteRequest;
import kr.mj.gollaba.poll.entity.Poll;
import kr.mj.gollaba.poll.repository.PollRepository;
import kr.mj.gollaba.poll.type.PollingResponseType;
import kr.mj.gollaba.unit.poll.factory.OptionFactory;
import kr.mj.gollaba.unit.poll.factory.PollFactory;
import kr.mj.gollaba.unit.poll.factory.VoterFactory;
import kr.mj.gollaba.unit.user.factory.UserFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PollControllerTest extends IntegrationTest {

    @Autowired
    private PollRepository pollRepository;

    @DisplayName("투표 생성")
    @Test
    public void create_poll() throws Exception {
        //given
        MultiValueMap request = generateCreateRequest();

        //when
        ResultActions resultActions = mvc.perform(multipart(Const.ROOT_URL + "/polls")
                .params(request)
                .param("endedAt", "2023-10-02T06:24:28.884Z")
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andDo(print())
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
                .andDo(print())
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
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("pollId").isNumber());
    }

    @DisplayName("투표하기")
    @Test
    public void vote() throws Exception {
        //given
        String url = Const.ROOT_URL + "/vote";

        VoteRequest request = new VoteRequest();
        request.setPollId(PollFactory.TEST_ID);
        request.setUserId(UserFactory.TEST_ID);
        request.setOptionIds(List.of(OptionFactory.TEST_ID));
        request.setVoterName(VoterFactory.TEST_VOTER_NAME);
        request.setIpAddress(VoterFactory.TEST_IP_ADDRESS);

        //when
        ResultActions resultActions = mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andDo(print())
                .andExpect(status().isCreated());


    }

    @DisplayName("투표 수정")
    @WithUserDetails(value = UserFactory.TEST_UNIQUE_ID)
    @Test
    void update_poll() throws Exception {
        //given
        final Long pollId = 10L;
        String testTitle = "테스트 타이틀입니당";
        String url = Const.ROOT_URL + String.format("/polls/%d/update", pollId);
        LocalDateTime testEndedAt = LocalDateTime.now().plusDays(2L);
        UpdatePollRequest request = new UpdatePollRequest();
        request.setTitle(testTitle);
        request.setEndedAt(testEndedAt);
        File file = ResourceUtils.getFile("classpath:test_image.jpeg");
        InputStream inputStream = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile(
                "pollImage",
                "test.png",
                "image/png",
                inputStream);
        MultiValueMap<String, String> params = MultiValueMapGenerator.generate(request);
        params.set("options[" + 0 + "].description", "test 항목" + 1);
        params.set("options[" + 1 + "].description", "test 항목" + 2);

        //when
        ResultActions resultActions = mvc.perform(multipart(url)
                .file(multipartFile)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .params(params)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andDo(print())
                .andExpect(status().isOk());

        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new GollabaException(GollabaErrorCode.NOT_EXIST_POLL));

        assertThat(poll.getTitle()).isEqualTo(testTitle);
        assertThat(poll.getEndedAt()).isEqualTo(testEndedAt);
        assertThat(poll.getPollImageUrl()).isNotBlank();
        assertThat(poll.getOptions().size()).isEqualTo(6);
    }

    private MultiValueMap<String, String> generateCreateRequest() {
        CreatePollRequest request = new CreatePollRequest();
        MultiValueMap<String, String> result = new LinkedMultiValueMap<>();

        request.setTitle(PollFactory.TEST_TITLE);
        request.setCreatorName(PollFactory.TEST_CREATOR_NAME);
        request.setEndedAt(PollFactory.TEST_ENDED_AT);
        request.setResponseType(PollingResponseType.SINGLE);
        request.setIsBallot(false);

        List<CreatePollRequest.OptionDto> optionDtos = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            CreatePollRequest.OptionDto optionDto = new CreatePollRequest.OptionDto();
            optionDto.setDescription("항목" + i);

            String optionString;

            try {
                optionString = objectMapper.writeValueAsString(optionDto);
            }catch (JsonProcessingException e) {
                optionString = null;
            }

            optionDtos.add(optionDto);
            result.set("options[" + i + "].description", "항목" + i);
        }

        request.setOptions(optionDtos);


        result.set("title", PollFactory.TEST_TITLE);
        result.set("creatorName", PollFactory.TEST_CREATOR_NAME);
        result.set("responseType", PollingResponseType.SINGLE.name());
        result.set("isBallot", Boolean.FALSE.toString());

        return result;
    }


}
