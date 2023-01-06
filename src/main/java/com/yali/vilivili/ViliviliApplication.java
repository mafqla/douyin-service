package com.yali.vilivili;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class ViliviliApplication {

    public static void main(String[] args) {
        SpringApplication.run(ViliviliApplication.class, args);
    }

}
