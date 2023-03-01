package com.microservices.demo.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "kafka-producer-config")
public class KafkaProducerConfigData {

    private String keySerializerClass;
    private String valueSerializerClass;
    private String compressionType;//sıkıştırma türü (snappy, gzip, lz4)
    private String acks;//acknowledgement (0, 1, all)
    private Integer batchSize;//batch boyutu
    private Integer batchSizeBoostFactor;//batch boyutu artış faktörü
    private Integer lingerMs;//batch gönderme süresi
    private Integer maxRequestSize;//max request boyutu
    private Integer requestTimeoutMs;//request timeout süresi
    private Integer retryCount;//tekrar sayısı

}
