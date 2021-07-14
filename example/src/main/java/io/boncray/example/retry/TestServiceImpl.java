package io.boncray.example.retry;

import io.boncray.core.aspect.retry.Retry;
import org.springframework.stereotype.Component;

/**
 * @author cca
 * @version 1.0
 * @date 2021/6/28 11:13
 */
@Component
public class TestServiceImpl implements TestService {


    @Override
    @Retry(retryFor = RuntimeException.class)
    public void testRetry() {
        throw new IllegalArgumentException("333");
    }
}
