package com.microservices.demo.twittertoKafkaService.runner;

import twitter4j.TwitterException;

public interface StreamRunner {
    void run() throws TwitterException;
}
