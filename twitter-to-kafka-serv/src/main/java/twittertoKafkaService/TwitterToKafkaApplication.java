package twittertoKafkaService;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import twittertoKafkaService.config.TwitterToKafkaServConfigData;
import twittertoKafkaService.runner.StreamRunner;

import java.util.Arrays;


@SpringBootApplication
@RequiredArgsConstructor
public class TwitterToKafkaApplication implements CommandLineRunner {
    private final Logger LOG = LoggerFactory.getLogger(TwitterToKafkaApplication.class);
    private final TwitterToKafkaServConfigData twitterToKafkaServConfigData;
    private final StreamRunner streamRunner;

    public static void main(String[] args) {
        SpringApplication.run(TwitterToKafkaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("TwitterToKafkaApplication is running");
        LOG.info(Arrays.toString(twitterToKafkaServConfigData.getTwitterKeywords().toArray()));
        LOG.info(twitterToKafkaServConfigData.getWelcomeMessage());
        streamRunner.run();
    }



    /*
    @Override
    public void onApplicationEvent(ApplicationEvent event) { //from   implements ApplicationListener and it will run one time when the application start
    }
     */

}
