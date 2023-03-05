package com.microservices.demo.twittertoKafkaService.init.impl;


import com.microservices.demo.client.KafkaAdminClient;
import com.microservices.demo.config.KafkaConfigData;
import com.microservices.demo.twittertoKafkaService.init.StreamInitializer;
import org.springframework.stereotype.Component;

@Component
public class KafkaStreamInitializer implements StreamInitializer {

    private KafkaConfigData kafkaConfigData;
    private KafkaAdminClient kafkaAdminClient;

    public KafkaStreamInitializer(KafkaConfigData kafkaConfigData, KafkaAdminClient kafkaAdminClient) {
        this.kafkaConfigData = kafkaConfigData;
        this.kafkaAdminClient = kafkaAdminClient;
    }

    @Override
    public void init() {
        System.out.println("Topics with name " + kafkaConfigData.getTopicNamesToCreate().toArray() + " is ready for operations!");
    }
}
