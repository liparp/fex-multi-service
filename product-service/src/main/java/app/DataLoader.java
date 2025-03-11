package app;

import app.model.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(ProductRepository repository) {
        return args -> {
            repository.save(new Product(null, "Laptop", "High performance laptop", new BigDecimal("999.99"), 10));
            repository.save(new Product(null, "Smartphone", "Latest app.model smartphone", new BigDecimal("499.99"), 20));
            repository.save(new Product(null, "Headphones", "Noise cancelling headphones", new BigDecimal("149.99"), 15));
        };
    }
}