package kr.mj.gollaba.unit.poll.service;

import kr.mj.gollaba.poll.dto.CreatePollRequest;
import kr.mj.gollaba.poll.entity.Poll;
import kr.mj.gollaba.poll.repository.PollQueryRepository;
import kr.mj.gollaba.poll.repository.PollRepository;
import kr.mj.gollaba.poll.service.PollService;
import kr.mj.gollaba.poll.type.PollingResponseType;
import kr.mj.gollaba.unit.common.ServiceTest;
import kr.mj.gollaba.unit.poll.factory.PollingFactory;
import kr.mj.gollaba.unit.user.factory.UserFactory;
import kr.mj.gollaba.user.entity.User;
import kr.mj.gollaba.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class PollServiceTest extends ServiceTest {

    @InjectMocks
    private PollService pollService;

    @Mock
    private PollQueryRepository pollQueryRepository;

    @Mock
    private PollRepository pollRepository;

    @Mock
    private UserRepository userRepository;

    @DisplayName("create 메서드는")
    @Nested
    class save {

        @DisplayName("회원일 경우")
        @Nested
        class case_login_user {

            @DisplayName("작성자 등록 후 투표를 저장한다.")
            @Test
            void register_user_and_save_poll() {
                //given
                User creator = UserFactory.createWithId();
                CreatePollRequest request = generateRequest();
                request.setUserId(creator.getId());

                given(userRepository.findById(anyLong()))
                        .willReturn(Optional.of(creator));

                //when
                pollService.create(request);

                //then
                verify(userRepository, times(1)).findById(eq(UserFactory.TEST_ID));
                verify(pollRepository, times(1)).save(any(Poll.class));
            }
        }

        @DisplayName("비회원일 경우")
        @Nested
        class case_not_login_user {

            @DisplayName("작성자 등록 후 투표를 저장한다.")
            @Test
            void save_poll() {
                //given
                CreatePollRequest request = generateRequest();

                //when
                pollService.create(request);

                //then
                verify(pollRepository, times(1)).save(any(Poll.class));
            }
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
}
