package kr.mj.gollaba.integration.common;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Tag(IntegrationConst.INTEGRATION_TEST_TAG_NAME)
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = { "spring.config.location=classpath:private/application-test.yaml"})
@ActiveProfiles("test")
public class S3UploadServiceTest {


}
