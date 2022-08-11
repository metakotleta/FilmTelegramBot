package ru.rvukolov.testbottelegram;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestBotTelegramApplication {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(TestBotTelegramApplication.class);

        SpringApplication.run(TestBotTelegramApplication.class, args);
        logger.info("Application start");
    }

}
