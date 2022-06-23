package com.xinbo.chainblock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ChainblockApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChainblockApplication.class, args);
    }

}
