package twittertoKafkaService.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "twitter-to-kafka-service")
public class TwitterToKafkaServConfigData {
    private List<String> twitterKeywords;
    private String welcomeMessage; //from application.properties they have to be same name with the properties
    private String twitterV2BaseUrl;
    private String twitterV2RulesBaseUrl;
    private String twitterV2BearerToken;
}
