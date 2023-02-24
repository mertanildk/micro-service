package com.microservices.demo.twittertoKafkaService.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import twitter4j.StatusAdapter;

@Component//these are bean and they are singleton by default,@Controller,@Service,@Repository,@Configuration
public class TwitterKafkaStatusListener extends StatusAdapter {

    private static final Logger LOG= LoggerFactory.getLogger(TwitterKafkaStatusListener.class);
    @Override
    public void onStatus(twitter4j.Status status) {
        LOG.info("onStatus: {}",status.getText());
    }
}
