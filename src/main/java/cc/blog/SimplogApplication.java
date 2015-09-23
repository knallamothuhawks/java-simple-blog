package cc.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ContextConfiguration;

import cc.blog.test.config.DataSourceConfig;

@SpringBootApplication
public class SimplogApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimplogApplication.class, args);
    }
}
