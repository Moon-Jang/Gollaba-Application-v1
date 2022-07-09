package kr.mj.polling.unit.common;

import kr.mj.polling.config.JpaConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(JpaConfig.class)
public abstract class RepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    public void flushAndClear() {
        testEntityManager.flush();
        testEntityManager.clear();
    }

}
