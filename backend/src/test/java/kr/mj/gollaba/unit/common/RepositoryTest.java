package kr.mj.gollaba.unit.common;

import kr.mj.gollaba.config.JpaConfig;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Tag("UnitTest")
@ExtendWith(SpringExtension.class)
@DataJpaTest
@ComponentScan(basePackages = "kr.mj.gollaba.*.repository")
@Import(JpaConfig.class)
@ActiveProfiles("test")
public abstract class RepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    public void flushAndClear() {
        testEntityManager.flush();
        testEntityManager.clear();
    }

}
