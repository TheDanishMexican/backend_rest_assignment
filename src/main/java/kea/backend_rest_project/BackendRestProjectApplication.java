package kea.backend_rest_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BackendRestProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendRestProjectApplication.class, args);
    }

}
