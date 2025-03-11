package app;

import app.model.ProductDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ProductServiceClient {

    private final WebClient webClient;

    public ProductServiceClient(@Value("${product-service.url}") String productServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(productServiceUrl)
                .build();
    }

    public Mono<ProductDTO> getProductById(Long productId) {
        return webClient.get()
                .uri("/{id}", productId)
                .retrieve()
                .bodyToMono(ProductDTO.class);
    }

    public Mono<Void> updateProductStock(Long productId, int quantity) {
        return webClient.put()
                .uri("/{id}/stock?quantity={quantity}", productId, quantity)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
