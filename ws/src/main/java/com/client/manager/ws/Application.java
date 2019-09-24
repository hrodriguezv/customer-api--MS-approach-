package com.client.manager.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
        exclude = {
                org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
        }
)
@EnableJpaRepositories(basePackages = {"com.client.manager.core"})
@ComponentScan(basePackages = {"com.client.manager.ws", "com.client.manager.core", "com.client.manager.entities"})
@EntityScan(basePackages = {"com.client.manager.entities"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}