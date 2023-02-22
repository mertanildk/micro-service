package twittertoKafkaService.runner.impl;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twittertoKafkaService.config.TwitterToKafkaServConfigData;
import twittertoKafkaService.listener.TwitterKafkaStatusListener;
import twittertoKafkaService.runner.StreamRunner;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
@ConditionalOnExpression("${twitter-to-kafka-service.enable-mock-tweets} && not ${twitter-to-kafka-service.enable-v2-tweets}")
//if haveValue is false, then this bean will be created
//if haveValue is true, then this bean will not be created
public class TwitterKafkaStreamRunner implements StreamRunner {
    private static final Logger LOG = LoggerFactory.getLogger(TwitterKafkaStreamRunner.class);
    private final TwitterToKafkaServConfigData twitterToKafkaServConfigData;
    private final TwitterKafkaStatusListener twitterKafkaStatusListener;
    private  TwitterStream twitterStream;

    @Override
    public void run() {
        twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(twitterKafkaStatusListener);
        addFilter();
    }
    @PreDestroy
    public void shutDown(){
        if (twitterStream !=null){
            LOG.info("Twitter stream is closing!");
            twitterStream.shutdown();
        }
    }

    private void addFilter() {
        String[] keywords = twitterToKafkaServConfigData.getTwitterKeywords().toArray(new String[0]);
        FilterQuery filterQuery = new FilterQuery(keywords);
        twitterStream.filter(filterQuery);
        LOG.info("Started filtering Twitter stream for keywords {}", Arrays.toString(keywords));
    }
}
