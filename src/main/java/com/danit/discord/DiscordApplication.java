package com.danit.discord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DiscordApplication {
    private static final Logger logger = LoggerFactory.getLogger(DiscordApplication.class);

    public static void main(String[] args) {
        logger.info("Discord API started!");

        SpringApplication.run(DiscordApplication.class, args);
    }

}
