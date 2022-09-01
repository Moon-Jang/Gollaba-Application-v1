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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@Tag(IntegrationConst.INTEGRATION_TEST_TAG_NAME)
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = { "spring.config.location=classpath:private/application-test.yaml"})
@ActiveProfiles("test")
public class CryptoUtilsTest {


	private final String encryptKey;

	private final CryptUtils cryptUtils;

	public CryptoUtilsTest(@Value("${security.aes.encrypt-key}") String encryptKey) {
		this.encryptKey = encryptKey;
		this.cryptUtils = new CryptUtils(encryptKey);
	}

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

}
