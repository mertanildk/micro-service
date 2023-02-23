package twittertoKafkaService.runner.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import twitter4j.TwitterException;
import twittertoKafkaService.config.TwitterToKafkaServConfigData;
import twittertoKafkaService.runner.StreamRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
@Component
@RequiredArgsConstructor
@ConditionalOnExpression("${twitter-to-kafka-service.enable-v2-tweets} && not ${twitter-to-kafka-service.enable-mock-tweets}")
 */
//if the property is not set, it will be true by default
//conditionalOnProperty is used to enable or disable beans based on the value of a property
//if enable-v2-tweets is true, then this bean will be created
//if enable-v2-tweets is false, then this bean will not be created
public class TwitterV2KafkaStreamRunner { //implements StreamRunner

    /*
    private static final Logger LOG = LoggerFactory.getLogger(TwitterV2KafkaStreamRunner.class);

    private final TwitterToKafkaServConfigData twitterToKafkaServiceConfigData;

    private final TwitterV2StreamHelper twitterV2StreamHelper;


    @Override
    public void run() {
        String bearerToken = twitterToKafkaServiceConfigData.getTwitterV2BearerToken();
        if (null != bearerToken) {
            try {
                twitterV2StreamHelper.setupRules(bearerToken, getRules());
                twitterV2StreamHelper.connectStream(bearerToken);
            } catch (IOException | URISyntaxException e) {
                LOG.error("Error streaming tweets!", e);
                throw new RuntimeException("Error streaming tweets!", e);
            }
        } else {
            LOG.error("There was a problem getting your bearer token. " +
                    "Please make sure you set the TWITTER_BEARER_TOKEN environment variable");
            throw new RuntimeException("There was a problem getting your bearer token. +" +
                    "Please make sure you set the TWITTER_BEARER_TOKEN environment variable");
        }

    }

    private Map<String, String> getRules() {
        List<String> keywords = twitterToKafkaServiceConfigData.getTwitterKeywords();
        Map<String, String> rules = new HashMap<>();
        for (String keyword : keywords) {
            rules.put(keyword, "Keyword: " + keyword);
        }
        LOG.info("Created filter for twitter stream for keywords: {}", keywords);
        return rules;
    }

     */

}
