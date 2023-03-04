package com.microservices.demo.twittertoKafkaService.init.impl;

import com.microservices.demo.config.KafkaConfigData;
import com.microservices.demo.twittertoKafkaService.init.StreamInitializer;
import kafka.admin.client.KafkaAdminClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaStreamInitializer implements StreamInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaStreamInitializer.class);
    private final KafkaConfigData kafkaConfigData;
    private final KafkaAdminClient kafkaAdminClient;
    @Override
    public void init() {
        kafkaAdminClient.createTopics();
        kafkaAdminClient.checkSchemaRegistery();
        LOG.info("Topics with name {} created successfully", kafkaConfigData.getTopicNamesToCreate().toArray());


    }
}
