package com.microservices.demo.config.service;

import example.avro.TwitterAvroModel;
import jakarta.annotation.PreDestroy;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.CompletableFuture;

public class TwitterKafkaProducerImp implements KafkaProducer<Long, TwitterAvroModel> {
    private final Logger LOG = LoggerFactory.getLogger(TwitterKafkaProducerImp.class);

    private KafkaTemplate<Long, TwitterAvroModel> kafkaTemplate;

    @Override
    public void send(String topicName, Long key, TwitterAvroModel message) {

        LOG.info("Sending message = {} to topic {}", message, topicName);

        ListenableFuture<SendResult<Long, TwitterAvroModel>> kafkaResultFuture = kafkaTemplate.send(topicName, key, message);
        CompletableFuture<SendResult<Long, TwitterAvroModel>> completableFuture = kafkaResultFuture.completable();
        addCallBack(topicName, completableFuture);


    }

    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            LOG.info("Closing Kafka Producer");
            kafkaTemplate.destroy();
        }
    }

    private void addCallBack(String topicName, CompletableFuture<SendResult<Long, TwitterAvroModel>> completableFuture) {
        completableFuture.whenComplete((result, ex) -> {
            if (ex != null) {
                LOG.error("Error while sending message to topic {}", topicName, ex);
            } else {
                RecordMetadata recordMetadata = result.getRecordMetadata();
                LOG.debug("Received new metadata. Topic: {}, Partition: {}, Offset: {}, Timestamp: {}",
                        recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset(),
                        recordMetadata.timestamp(), System.nanoTime());
            }
        });
    }
}
