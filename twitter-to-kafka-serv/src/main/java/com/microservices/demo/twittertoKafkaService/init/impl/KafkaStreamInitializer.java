package com.microservices.demo.twittertoKafkaService.init.impl;

import com.microservices.demo.config.KafkaConfigData;
import com.microservices.demo.twittertoKafkaService.init.StreamInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class KafkaStreamInitializer implements StreamInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaStreamInitializer.class);
    private KafkaConfigData kafkaConfigData;


    @Override
    public void init() {

    }

}
