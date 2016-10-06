package co.edu.usta.telco.iot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableMongoAuditing
public class MainApplication extends WebMvcConfigurerAdapter {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
