package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalTime;


@Slf4j
@SpringBootApplication(scanBasePackages = "com.example.demo")
public class ScheduledApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduledApplication.class, args);
        log.info(
                "======================== started up successfully at {} {} ========================",
                LocalDate.now(),
                LocalTime.now()
        );
    }
}
