package com.sinosoft.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class},scanBasePackages = {"com.sinosoft"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class ,args);
    }
}
