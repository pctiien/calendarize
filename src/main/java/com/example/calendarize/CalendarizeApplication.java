package com.example.calendarize;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.example.calendarize")

public class CalendarizeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalendarizeApplication.class, args);
    }

}
