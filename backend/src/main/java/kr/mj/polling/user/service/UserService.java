package kr.mj.polling.user.service;

import kr.mj.polling.exception.SampleErrorCode;
import kr.mj.polling.exception.SampleException;
import kr.mj.polling.user.dto.SignupRequest;
import kr.mj.polling.user.dto.SignupResponse;
import kr.mj.polling.user.entity.User;
import kr.mj.polling.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupResponse save(SignupRequest request) {
        if (userRepository.existsByUniqueId(request.getUniqueId())) {
            throw new SampleException(SampleErrorCode.ALREADY_EXIST_USER);
        }

        User user = request.toEntity(passwordEncoder);
        User saveResult = userRepository.save(user);

        return SignupResponse.builder()
                .userId(saveResult.getId())
                .build();
    }

}
