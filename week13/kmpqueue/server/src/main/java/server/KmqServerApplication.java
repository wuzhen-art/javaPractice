package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 */
@SpringBootApplication(scanBasePackages = {"server"})
public class KmqServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KmqServerApplication.class, args);
    }

}
