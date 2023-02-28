package kafka.admin.config;

import com.microservices.demo.config.KafkaConfigData;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

import java.util.Map;

@Configuration
@EnableRetry
public class KafkaAdminConfig {
    private KafkaConfigData kafkaConfigData;

    @Bean
    public AdminClient adminClient() {//Manage and inspect brokers, topics and configurations
        return AdminClient.create(Map.of(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigData.getBootstrapServers()));
    }

}
