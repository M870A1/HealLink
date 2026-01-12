package org.zerock.obj2026;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) // 보안 잠깐 제거


public class Obj2026Application {

    public static void main(String[] args) {
        SpringApplication.run(Obj2026Application.class, args);
    }

}
