package kr.mj.gollaba.unit.user.service;

import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import kr.mj.gollaba.unit.common.ServiceTest;
import kr.mj.gollaba.user.dto.UpdateRequest;
import kr.mj.gollaba.user.repository.UserRepository;
import kr.mj.gollaba.unit.user.factory.UserFactory;
import kr.mj.gollaba.user.dto.SignupRequest;
import kr.mj.gollaba.user.dto.SignupResponse;
import kr.mj.gollaba.user.entity.User;
import kr.mj.gollaba.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


class UserServiceTest extends ServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Spy
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@DisplayName("save 메서드는")
	@Nested
	class save {

		@DisplayName("현재 등록되지 않은 회원일 경우")
		@Nested
		class saveWhenCanRegister {

			@DisplayName("정상 응답객체를 반환한다.")
			@Test
			public void return_response() throws Exception {
				//given
				given(userRepository.existsByUniqueId(anyString()))
						.willReturn(false);
				given(userRepository.save(any(User.class)))
						.willReturn(UserFactory.createWithId());

				SignupRequest request = new SignupRequest();
				request.setId(UserFactory.TEST_UNIQUE_ID);
				request.setNickName(UserFactory.TEST_NICK_NAME);
				request.setPassword(UserFactory.TEST_PASSWORD);

				//when
				SignupResponse response = userService.create(request);

				//then
				assertThat(response.getUserId()).isEqualTo(UserFactory.TEST_ID);
				verify(userRepository, times(1)).existsByUniqueId(anyString());
				verify(userRepository, times(1)).save(any(User.class));
			}
		}

		@DisplayName("이미 등록된 아이디일 경우")
		@Nested
		class saveWhenDuplicate {

			@DisplayName("중복 회원 에러가 발생한다.")
			@Test
			public void error_by_exist_user() throws Exception {
				//given
				given(userRepository.existsByUniqueId(anyString()))
						.willReturn(true);

				SignupRequest request = new SignupRequest();
				request.setId(UserFactory.TEST_UNIQUE_ID);
				request.setNickName(UserFactory.TEST_NICK_NAME);
				request.setPassword(UserFactory.TEST_PASSWORD);

				//when then
				assertThatThrownBy(() -> userService.create(request))
						.as(GollabaErrorCode.ALREADY_EXIST_USER.getDescription())
						.isInstanceOf(GollabaException.class);

				verify(userRepository, times(1)).existsByUniqueId(eq(UserFactory.TEST_UNIQUE_ID));
			}
		}

		@DisplayName("이미 등록된 닉네임일 경우")
		@Nested
		class saveWhenDuplicateNickName {

			@DisplayName("중복 회원 에러가 발생한다.")
			@Test
			public void throw_exist_user_exception() throws Exception {
				//given
				given(userRepository.existsByUniqueId(anyString()))
						.willReturn(false);
				given(userRepository.existsByNickName(anyString()))
						.willReturn(true);

				SignupRequest request = new SignupRequest();
				request.setId(UserFactory.TEST_UNIQUE_ID);
				request.setNickName(UserFactory.TEST_NICK_NAME);
				request.setPassword(UserFactory.TEST_PASSWORD);

				//when then
				assertThatThrownBy(() -> userService.create(request))
						.as(GollabaErrorCode.ALREADY_EXIST_USER.getDescription())
						.isInstanceOf(GollabaException.class);

				verify(userRepository, times(1)).existsByUniqueId(eq(UserFactory.TEST_UNIQUE_ID));
				verify(userRepository, times(1)).existsByNickName(eq(UserFactory.TEST_NICK_NAME));
			}
		}
	}

	@DisplayName("updateNickName 메서드는")
	@Nested
	class updateNickName {

		@DisplayName("1. 요청값중 닉네임이 비어있을 경우")
		@Nested
		class when_empty_nickname {

			@DisplayName("요청 에러가 발생한다.")
			@Test
			void throw_invalid_params_exception() {
				//given
				UpdateRequest request = new UpdateRequest();
				request.setUpdateType(UpdateRequest.UpdateType.NICKNAME);
				request.setNickName(null);
				User user = UserFactory.createWithId();

				//when then
				assertThatThrownBy(() -> userService.updateNickName(request, user))
						.hasMessage("닉네임을 입력해주세요.")
						.isInstanceOf(GollabaException.class);

			}
		}

		@DisplayName("2. 변경하려는 닉네임이 이미 존재하는 닉네임일 경우")
		@Nested
		class when_duplicated_nickname {

			@DisplayName("닉네임 중첩 에러가 발생한다.")
			@Test
			void throw_ALREADY_EXIST_NICKNAME() {
				//given
				String testNickName = UserFactory.TEST_NICK_NAME + "님";
				given(userRepository.existsByNickName(eq(testNickName)))
						.willReturn(true);
				UpdateRequest request = new UpdateRequest();
				request.setUpdateType(UpdateRequest.UpdateType.NICKNAME);
				request.setNickName(testNickName);
				User user = UserFactory.createWithId();
				//when then
				assertThatThrownBy(() -> userService.updateNickName(request, user))
						.hasMessage(GollabaErrorCode.ALREADY_EXIST_NICKNAME.getDescription())
						.isInstanceOf(GollabaException.class);

				verify(userRepository, times(1)).existsByNickName(eq(testNickName));
			}
		}

		@DisplayName("3. 모든 검증을 통과한 경우")
		@Nested
		class success_all_validation {

			@DisplayName("닉네임을 업데이트 후 영속화한다.")
			@Test
			void update_nickname() {
			    //given
				String testNickName = UserFactory.TEST_NICK_NAME + "님";
				User user = UserFactory.createWithId();
				given(userRepository.existsByNickName(eq(testNickName)))
						.willReturn(false);
				given(userRepository.save(any(User.class)))
						.willReturn(user);
				UpdateRequest request = new UpdateRequest();
				request.setUpdateType(UpdateRequest.UpdateType.NICKNAME);
				request.setNickName(testNickName);

			    //when
			    userService.updateNickName(request, user);

			    //then
				verify(userRepository, times(1)).existsByNickName(eq(testNickName));
				verify(userRepository, times(1)).save(eq(user));
			}
		}
	}

	@DisplayName("updatePassword 메서드는")
	@Nested
	class updatePassword {

		@DisplayName("1. 입력한 현재 비밀번호가 저장된 비밀번호와 일치하지 않을 경우")
		@Nested
		class  when_not_matched_password{

			@DisplayName("비밀번호 불일치 에러가 발생한다.")
			@Test
			void throw_NOT_MATCHED_PASSWORD() {
			    //given
				String testPassword = UserFactory.TEST_PASSWORD + "123";
				given(passwordEncoder.matches(anyString(), anyString()))
						.willReturn(false);
				User user = UserFactory.createWithId();
			    UpdateRequest request = new UpdateRequest();
				request.setUpdateType(UpdateRequest.UpdateType.PASSWORD);
				request.setCurrentPassword(UserFactory.TEST_PASSWORD);
				request.setNewPassword(testPassword);

			    //when then
				assertThatThrownBy(() -> userService.updatePassword(request, user))
						.hasMessage(GollabaErrorCode.NOT_MATCHED_PASSWORD.getDescription())
						.isInstanceOf(GollabaException.class);
				verify(passwordEncoder, times(1)).matches(eq(UserFactory.TEST_PASSWORD), anyString());
			}
		}

	}

}