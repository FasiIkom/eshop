package id.ac.ui.cs.advprog.eshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EshopApplication {

    // Private constructor to prevent instantiation
    private EshopApplication() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static void main(String[] args) {
        SpringApplication.run(EshopApplication.class, args);
    }
}
