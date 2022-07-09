package kr.mj.polling.security.service;

import kr.mj.polling.exception.PollingAppErrorCode;
import kr.mj.polling.exception.PollingAppException;
import kr.mj.polling.security.PrincipalDetails;
import kr.mj.polling.user.entity.User;
import kr.mj.polling.unit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String uniqueId) throws UsernameNotFoundException {
        User user = userRepository.findByUniqueId(uniqueId)
                .orElseThrow(() -> new PollingAppException(PollingAppErrorCode.NOT_EXIST_USER_BY_UNIQUE_ID));

        return new PrincipalDetails(user);
    }

}
