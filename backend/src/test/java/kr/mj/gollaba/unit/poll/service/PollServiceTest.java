package kr.mj.gollaba.unit.poll.service;

import kr.mj.gollaba.common.service.S3UploadService;
import kr.mj.gollaba.common.util.CryptUtils;
import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import kr.mj.gollaba.poll.dto.*;
import kr.mj.gollaba.poll.entity.Option;
import kr.mj.gollaba.poll.entity.Poll;
import kr.mj.gollaba.poll.entity.Voter;
import kr.mj.gollaba.poll.repository.PollQueryRepository;
import kr.mj.gollaba.poll.repository.PollRepository;
import kr.mj.gollaba.poll.service.PollService;
import kr.mj.gollaba.poll.type.PollingResponseType;
import kr.mj.gollaba.unit.common.ServiceTest;
import kr.mj.gollaba.unit.poll.factory.OptionFactory;
import kr.mj.gollaba.unit.poll.factory.PollFactory;
import kr.mj.gollaba.unit.poll.factory.VoterFactory;
import kr.mj.gollaba.unit.user.factory.UserFactory;
import kr.mj.gollaba.user.entity.User;
import kr.mj.gollaba.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static kr.mj.gollaba.poll.type.PollingResponseType.SINGLE;
import static kr.mj.gollaba.unit.poll.factory.PollFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    @Mock
    private S3UploadService s3UploadService;

    @Mock
    private CryptUtils cryptUtils;

    @DisplayName("create 메서드는")
    @Nested
    class create {

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
                Poll poll = PollFactory.createWithId(null, OptionFactory.createList());

                given(userRepository.findById(anyLong()))
                        .willReturn(Optional.of(creator));
                given(pollRepository.save(any(Poll.class)))
                        .willReturn(poll);
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
                Poll poll = PollFactory.createWithId(null, OptionFactory.createList());

                given(pollRepository.save(any(Poll.class)))
                        .willReturn(poll);

                //when
                pollService.create(request);

                //then
                verify(pollRepository, times(1)).save(any(Poll.class));
            }
        }

        @DisplayName("투표 이미지가 있을 경우 경우")
        @Nested
        class has_poll_image {

            @DisplayName("투표 저장 후 투표 이미지를 업로드한다.")
            @Test
            void upload_image_after_save_poll() throws IOException {
                //given
                CreatePollRequest request = generateRequest();

                File file = ResourceUtils.getFile("classpath:test_image.jpeg");
                InputStream inputStream = new FileInputStream(file);

                request.setPollImage(new MockMultipartFile(
                        "image",
                        "test.png",
                        "image/png",
                        inputStream));
                Poll poll = PollFactory.createWithId(null, OptionFactory.createList());

                given(pollRepository.save(any(Poll.class)))
                        .willReturn(poll);
                given(s3UploadService.generateFileName(anyLong(), anyString()))
                        .willReturn("testName");
                given(s3UploadService.upload(anyString(), anyString(), any()))
                        .willReturn("testUrl");

                //when
                CreatePollResponse result = pollService.create(request);

                //then
                assertThat(result.getPollId()).isPositive();
                verify(pollRepository, times(2)).save(any(Poll.class));
                verify(s3UploadService, times(1)).generateFileName(anyLong(), anyString());
                verify(s3UploadService, times(1)).upload(anyString(), anyString(), any());
            }
        }

        private CreatePollRequest generateRequest() {
            CreatePollRequest request = new CreatePollRequest();

            request.setTitle(TEST_TITLE);
            request.setCreatorName(TEST_CREATOR_NAME);
            request.setEndedAt(LocalDateTime.now().plusDays(3L));
            request.setResponseType(SINGLE);
            request.setEndedAt(TEST_ENDED_AT);
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

    @DisplayName("findAll 메서드는")
    @Nested
    class findAll {

        @DisplayName("주어진 조건들을 통해 검색 후 결과를 리턴한다.")
        @Test
        void return_search_result() {
            //given
            List<Poll> polls = PollFactory.createList();
            given(pollQueryRepository.findAllCount(any(PollQueryFilter.class)))
                    .willReturn(150L);
            given(pollQueryRepository.findIds(any((PollQueryFilter.class))))
                    .willReturn(List.of(1L,2L,3L));
            given(pollQueryRepository.findAll(any(List.class)))
                    .willReturn(polls);
            FindAllPollRequest request = new FindAllPollRequest();
            request.setOffset(0);
            request.setLimit(15);

            //when
            FindAllPollResponse result = pollService.findAll(request);

            //then
            verify(pollQueryRepository, times(1)).findAllCount(any(PollQueryFilter.class));
            verify(pollQueryRepository, times(1)).findIds(any(PollQueryFilter.class));
            verify(pollQueryRepository, times(1)).findAll(any(List.class));
            assertThat(result.getPolls().size()).isEqualTo(polls.size());
        }
    }

    @DisplayName("find 메서드는")
    @Nested
    class find {

        @DisplayName("투표 id를 통해 검색 후 결과를 리턴한다.")
        @Test
        void return_search_result() {
            //given
            Poll poll = PollFactory.createWithId(null, OptionFactory.createList());
            given(pollQueryRepository.findById(any(Long.class)))
                    .willReturn(Optional.of(poll));

            //when
            FindPollResponse result = pollService.find(poll.getId());

            //then
            verify(pollQueryRepository, times(1)).findById(eq(poll.getId()));
            assertThat(result.getPollId()).isEqualTo(poll.getId());
        }
    }

    @DisplayName("vote 메서드는")
    @Nested
    class vote {

        @DisplayName("단일 투표일 경우")
        @Nested
        class when_response_type_single {

            @DisplayName("투표를 중복으로하면 유효성 검사를 실패한다.")
            @Test
            void throw_exception_by_multi_option() {
                //given
                List<Option> options = OptionFactory.createListWithId();
                Poll poll = PollFactory.createWithId(null, options);
                poll.updateResponseType(SINGLE);
                VoteRequest request = new VoteRequest();
                request.setPollId(PollFactory.TEST_ID);
                request.setOptionIds(poll.getOptions().stream().map(Option::getId).collect(Collectors.toList()));
                request.setVoterName(VoterFactory.TEST_VOTER_NAME);
                request.setUserId(null);
                request.setIpAddress(VoterFactory.TEST_IP_ADDRESS);
                given(pollQueryRepository.findById(anyLong()))
                        .willReturn(Optional.of(poll));

                //when then
                assertThatThrownBy(() -> pollService.vote(request))
                        .hasMessage(GollabaErrorCode.NOT_AVAILABLE_MULTI_VOTE_BY_RESPONSE_TYPE.getDescription())
                        .isInstanceOf(GollabaException.class);

                verify(pollQueryRepository, times(1)).findById(eq(PollFactory.TEST_ID));
            }

        }

        @DisplayName("익명 투표일 경우")
        @Nested
        class when_ballot_poll {

            @DisplayName("투표자를 이름을 입력하면 유효성 검사를 실패한다.")
            @Test
            void throw_exception_by_voter_name() {
                //given
                List<Option> options = OptionFactory.createListWithId();
                Poll poll = PollFactory.createWithIdAndBallot(null, options);
                poll.updateResponseType(PollingResponseType.MULTI);
                VoteRequest request = new VoteRequest();
                request.setPollId(PollFactory.TEST_ID);
                request.setOptionIds(poll.getOptions().stream().map(Option::getId).collect(Collectors.toList()));
                request.setVoterName(VoterFactory.TEST_VOTER_NAME);
                request.setUserId(null);
                request.setIpAddress(VoterFactory.TEST_IP_ADDRESS);
                given(pollQueryRepository.findById(anyLong()))
                        .willReturn(Optional.of(poll));

                //when then
                assertThatThrownBy(() -> pollService.vote(request))
                        .hasMessage(GollabaErrorCode.DONT_NEED_VOTER_NAME.getDescription())
                        .isInstanceOf(GollabaException.class);

                verify(pollQueryRepository, times(1)).findById(eq(PollFactory.TEST_ID));
            }
        }

        @DisplayName("동일한 ip 주소로 여러번 요청활 경우")
        @Nested
        class when_duplicate_ip_address {

            @DisplayName("ip 중복으로 유효성 검사를 실패한다.")
            @Test
            void throw_exception_by_duplicated_ipAddress() {
                //given
                List<Option> options = OptionFactory.createListWithId();
                Voter voter = VoterFactory.createWithId(options.get(0), null);
                Poll poll = PollFactory.createWithId(null, options);
                poll.updateResponseType(PollingResponseType.MULTI);
                VoteRequest request = new VoteRequest();
                request.setPollId(PollFactory.TEST_ID);
                request.setOptionIds(poll.getOptions().stream().map(Option::getId).collect(Collectors.toList()));
                request.setVoterName(VoterFactory.TEST_VOTER_NAME);
                request.setUserId(null);
                request.setIpAddress(VoterFactory.TEST_IP_ADDRESS);
                given(pollQueryRepository.findById(anyLong()))
                        .willReturn(Optional.of(poll));
                given(cryptUtils.decrypt(anyString()))
                        .willReturn(VoterFactory.TEST_IP_ADDRESS);

                //when then
                assertThatThrownBy(() -> pollService.vote(request))
                        .hasMessage(GollabaErrorCode.ALREADY_VOTE.getDescription())
                        .isInstanceOf(GollabaException.class);

                verify(pollQueryRepository, times(1)).findById(eq(PollFactory.TEST_ID));
            }

        }

        @DisplayName("정상 요청일 경우")
        @Nested
        class when_request_is_valid {

            @DisplayName("해당 투표에 투표자를 저장한다.")
            @Test
            void save_voter() {
                //given
                User user = UserFactory.create();
                List<Option> options = OptionFactory.createListWithId();
                Poll poll =PollFactory.createWithId(user, options);
                given(cryptUtils.encrypt(anyString()))
                        .willReturn("encryptedString");
                given(userRepository.findById(anyLong()))
                        .willReturn(Optional.of(user));
                given(pollQueryRepository.findById(anyLong()))
                        .willReturn(Optional.of(poll));
                given(pollRepository.save(any(Poll.class)))
                        .willReturn(poll);

                VoteRequest request = new VoteRequest();
                request.setPollId(PollFactory.TEST_ID);
                request.setUserId(UserFactory.TEST_ID);
                request.setOptionIds(List.of(options.get(0).getId()));
                request.setVoterName(VoterFactory.TEST_VOTER_NAME);
                request.setIpAddress(VoterFactory.TEST_IP_ADDRESS);

                //when
                pollService.vote(request);

                //then
                verify(cryptUtils, times(1)).encrypt(eq(VoterFactory.TEST_IP_ADDRESS));
                verify(userRepository, times(1)).findById(eq(UserFactory.TEST_ID));
                verify(pollQueryRepository, times(1)).findById(eq(PollFactory.TEST_ID));
                verify(pollRepository, times(1)).save(eq(poll));
            }

        }
    }
}
