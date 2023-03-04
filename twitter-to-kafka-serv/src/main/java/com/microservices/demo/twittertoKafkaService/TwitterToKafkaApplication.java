package com.microservices.demo.twittertoKafkaService;

import com.microservices.demo.twittertoKafkaService.runner.StreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import com.microservices.demo.config.TwitterToKafkaServConfigData;

import java.util.Arrays;


@SpringBootApplication
@ComponentScan(basePackages = "com.microservices.demo")
//paketleri araması için ortak bir paket oluşturduk ve onu belirttik ve bu paketlerdeki classları bulup yükleyecek
public class TwitterToKafkaApplication implements CommandLineRunner {
    private final Logger LOG = LoggerFactory.getLogger(TwitterToKafkaApplication.class);
    private final TwitterToKafkaServConfigData twitterToKafkaServConfigData;
    private final StreamRunner streamRunner;

    public TwitterToKafkaApplication(TwitterToKafkaServConfigData twitterToKafkaServConfigData, StreamRunner streamRunner) {
        this.twitterToKafkaServConfigData = twitterToKafkaServConfigData;
        this.streamRunner = streamRunner;
    }

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
