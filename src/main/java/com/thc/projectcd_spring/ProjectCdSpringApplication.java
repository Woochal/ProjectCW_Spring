package com.thc.projectcd_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ProjectCdSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectCdSpringApplication.class, args);
    }

}





