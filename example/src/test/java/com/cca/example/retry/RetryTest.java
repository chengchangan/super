package com.cca.example.retry;

import com.cca.example.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author cca
 * @version 1.0
 * @date 2021/6/28 14:23
 */
public class RetryTest extends BaseTest {

    @Autowired
    private TestService testService;

    @Test
    public void testRetry(){
        testService.testRetry();
    }

}
