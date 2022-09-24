package kr.mj.gollaba.integration.common;


import kr.mj.gollaba.common.util.CryptUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@Tag(IntegrationConst.INTEGRATION_TEST_TAG_NAME)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CryptUtils.class)
@TestPropertySource(properties = { "spring.config.location=classpath:private/application-test.yaml"})
@ActiveProfiles("test")
public class CryptoUtilsTest {

	@Autowired
	private CryptUtils cryptUtils;

	@DisplayName("문자열 암호화 테스트")
	@Test
	void cryptoTest() {
	    //given
		String str = "hello";

	    //when
	    String encryptedStr = cryptUtils.encrypt(str);
		System.out.println(encryptedStr);

		//then
		assertThat(cryptUtils.decrypt(encryptedStr)).isEqualTo(str);
	}

	@DisplayName("비밀번호 해시화 테스트")
	@Test
	void passwordHashTest() {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String result = passwordEncoder.encode("test1234*");
		System.out.println(result);
	}

}
