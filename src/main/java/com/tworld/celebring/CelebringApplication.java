package com.tworld.celebring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CelebringApplication {

    public static void main(String[] args) {
        SpringApplication.run(CelebringApplication.class, args);
    }

}
