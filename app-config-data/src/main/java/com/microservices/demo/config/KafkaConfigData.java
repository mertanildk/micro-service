package com.microservices.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka-config")
public class KafkaConfigData {
    private String bootstrapServers;
    private String schemaRegistryUrl;
    private String schemaRegistryUrlKey;
    private String topicName;
    private List<String> topicNamesToCreate;
    private Integer numPartitions;
    private Short   replicationFactor;
}
