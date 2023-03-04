package com.microservices.demo.twittertoKafkaService.listener;

import com.microservices.demo.config.KafkaConfigData;
import com.microservices.demo.twittertoKafkaService.transformer.TwitterStatusToAvroTransformer;
import config.service.KafkaProducer;
import example.avro.TwitterAvroModel;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import twitter4j.StatusAdapter;

@Component//this is bean and they are singleton by default,@Controller,@Service,@Repository,@Configuration
@RequiredArgsConstructor
public class TwitterKafkaStatusListener extends StatusAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterKafkaStatusListener.class);

    private final KafkaConfigData kafkaConfigData;
    private final KafkaProducer<Long, TwitterAvroModel> kafkaProducer;
    private final TwitterStatusToAvroTransformer twitterStatusToAvroTransformer;

    @Override
    public void onStatus(twitter4j.Status status) {
        LOG.info("Received status text {} sending to kafka topic {}", status.getText(), kafkaConfigData.getTopicName());
        TwitterAvroModel twitterAvroModel = twitterStatusToAvroTransformer.getTwitterAvroModelFromStatus(status);
        kafkaProducer.send(kafkaConfigData.getTopicName(), twitterAvroModel.getUserId(), twitterAvroModel);
        //producer mesajları Id'yi key olarak kullandığı için bir kullanıcıya ait tweetler aynı topic üzerinde olacak.


    }

}
