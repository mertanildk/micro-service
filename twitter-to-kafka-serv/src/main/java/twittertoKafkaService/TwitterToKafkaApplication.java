package twittertoKafkaService;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import twittertoKafkaService.config.TwitterToKafkaServConfigData;

import java.util.Arrays;
import java.util.List;


@SpringBootApplication
@RequiredArgsConstructor
public class TwitterToKafkaApplication implements CommandLineRunner {
    private final Logger LOG = LoggerFactory.getLogger(TwitterToKafkaApplication.class);
    private final TwitterToKafkaServConfigData twitterToKafkaServConfigData;

    public static void main(String[] args) {
        SpringApplication.run(TwitterToKafkaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("TwitterToKafkaApplication is running");
        LOG.info(Arrays.toString(twitterToKafkaServConfigData.getTwitterKeywords().toArray()));
        LOG.info(twitterToKafkaServConfigData.getWelcomeMessage());
    }



    /*
    @Override
    public void onApplicationEvent(ApplicationEvent event) { //from   implements ApplicationListener and it will run one time when the application start
    }
     */

}
