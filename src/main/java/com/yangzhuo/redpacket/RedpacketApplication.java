package com.yangzhuo.redpacket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RedpacketApplication {

    public static void main(String[] args) {

        SpringApplication.run(RedpacketApplication.class, args);
    }
}
