package code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        System.out.println("Starting Spring Boot Application");
        SpringApplication.run(MainApplication.class,args);
        System.out.println("Started Spring Boot Application");
    }
}
