package io.boncray.web;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/21 19:56
 */
@SpringBootTest(classes = BaseTest.Application.class)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class BaseTest {

    @Configuration
    @ComponentScan("io.boncray")
    public static class Application {
    }
}
