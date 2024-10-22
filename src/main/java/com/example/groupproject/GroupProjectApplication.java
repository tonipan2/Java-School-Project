package com.example.groupproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GroupProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(GroupProjectApplication.class, args);
    }

}

//note to self: izpolzvai inicializiranite service-i v kontroleri klasovete,
// zashtoto te sa private final, ako vikash obiknoveniq service gyrmi - anton