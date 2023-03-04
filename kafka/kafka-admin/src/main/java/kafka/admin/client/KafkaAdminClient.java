package kafka.admin.client;

import com.microservices.demo.config.KafkaConfigData;
import com.microservices.demo.config.RetryConfigData;
import kafka.admin.config.WebClientConfig;
import kafka.admin.exception.KafkaClientException;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicListing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.http.HttpClient;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class KafkaAdminClient{
    private static final String MAX_TOPIC_EXP_MESSAGE = "Reacted max number of retry for creating kafka topics!";
    private final Logger LOG = LoggerFactory.getLogger(KafkaAdminClient.class);
    private KafkaConfigData kafkaConfigData;
    private RetryConfigData retryConfigData;
    private AdminClient adminClient;
    private RetryTemplate retryTemplate;//call a method with retry logic configured in the retry config.
    private WebClient webClient;


    public void createTopics() {
        try {
            retryTemplate.execute(this::doCreateTopics);
        } catch (Exception t) {
            throw new KafkaClientException(MAX_TOPIC_EXP_MESSAGE, t);
        }
        checkTopicsCreated();
    }


    private void checkTopicsCreated() {
        Collection<TopicListing> topicListings = getTopics();
        int retryCount = 1;
        Integer maxRetry = retryConfigData.getMaxAttempts();
        Double multiplier = retryConfigData.getMultiplier();
        Double sleepTimeMs = retryConfigData.getSleepTime();
        for (String topic : kafkaConfigData.getTopicNamesToCreate()) {
            while (!isTopicCreated(topicListings, topic)) {
                checkMaxRetry(retryCount++, maxRetry);
                sleep(Long.parseLong(sleepTimeMs.toString()));
                sleepTimeMs += multiplier;
                topicListings = getTopics();
            }
        }
    }

    public void checkSchemaRegistery() {
        int retryCount = 1;
        Integer maxRetry = retryConfigData.getMaxAttempts();
        Double multiplier = retryConfigData.getMultiplier();
        Double sleepTimeMs = retryConfigData.getSleepTime();
        do {
            checkMaxRetry(retryCount++, maxRetry);
            sleep(Long.parseLong(sleepTimeMs.toString()));
            sleepTimeMs += multiplier;
        } while (getSchemaRegistryClient().is2xxSuccessful());
    }

    private HttpStatusCode getSchemaRegistryClient() {
        try {
            return webClient.method(HttpMethod.GET)
                    .uri(kafkaConfigData.getSchemaRegistryUrl())
                    .exchangeToMono(rs -> Mono.just(rs.mutate().build()))
                    .map(ClientResponse::statusCode)
                    .block();
        } catch (Exception e) {
            //SERVICE_UNAVAILABLE
            return HttpStatus.SERVICE_UNAVAILABLE;
        }
    }
    private void sleep(long parseLong) {
        try {
            Thread.sleep(parseLong);
        } catch (Exception e) {
            throw new KafkaClientException("Error while sleeping for waiting new created topics!");
        }
    }

    private void checkMaxRetry(Integer retry, Integer maxRetry) {
        if (retry > maxRetry) throw new KafkaClientException(MAX_TOPIC_EXP_MESSAGE);

    }

    private boolean isTopicCreated(Collection<TopicListing> topicListings, String topicName) {
        if (topicListings == null) return false;
        return topicListings.stream().map(TopicListing::name).toList().contains(topicName);
    }

    private Collection<TopicListing> getTopics() {
        Collection<TopicListing> topicListings;
        try {
            topicListings = retryTemplate.execute(this::doGetTopics);
        } catch (Throwable t) {
            throw new KafkaClientException(MAX_TOPIC_EXP_MESSAGE, t);
        }
        return topicListings;

    }

    private CreateTopicsResult doCreateTopics(RetryContext retryContext) {
        List<String> topicNames = kafkaConfigData.getTopicNamesToCreate();
        LOG.info("Creating {} topics, attempt {}", topicNames.size(), retryContext.getRetryCount());
        List<NewTopic> kafkaTopics = topicNames.stream().map(topic -> new NewTopic(
                topic.trim(),
                kafkaConfigData.getNumPartitions(),
                kafkaConfigData.getReplicationFactor()
        )).toList();
        return adminClient.createTopics(kafkaTopics);
    }

    private Collection<TopicListing> doGetTopics(RetryContext retryContext)
            throws ExecutionException, InterruptedException {
        LOG.info("Reading kafka {} topic {}, attempt {}",
                kafkaConfigData.getTopicNamesToCreate().toArray(), retryContext.getRetryCount());
        Collection<TopicListing> topicListings = adminClient.listTopics().listings().get();
        if (topicListings != null) {
            topicListings.forEach(topicListing -> LOG.debug("Topic with name {}", topicListing.name()));
        }
        return topicListings;


    }
}
