package twittertoKafkaService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TwitterToKafkaApplication implements CommandLineRunner {
    public static void main(String[] args) {SpringApplication.run(TwitterToKafkaApplication.class, args);}
    @Override
    public void run(String... args) throws Exception {System.out.println("Application started");}



    /*
    @Override
    public void onApplicationEvent(ApplicationEvent event) { //from   implements ApplicationListener and it will run one time when the application start
    }
     */

}
