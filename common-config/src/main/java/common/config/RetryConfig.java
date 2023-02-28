package common.config;


import com.microservices.demo.config.RetryConfigData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class RetryConfig {
    private RetryConfigData retryConfigData;



    @Bean
    public RetryTemplate retryConfigData() {
        RetryTemplate retryTemplate = new RetryTemplate();
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(retryConfigData.getInitialIntervalMs());
        backOffPolicy.setMaxInterval(retryConfigData.getMaxIntervalMs());
        backOffPolicy.setMultiplier(retryConfigData.getMultiplier());
        retryTemplate.setBackOffPolicy(backOffPolicy);

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(retryConfigData.getMaxAttempts());
        retryTemplate.setRetryPolicy(retryPolicy);

        return retryTemplate;
    }

}
