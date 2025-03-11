package app;

import jakarta.validation.Valid;
import app.model.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productService.getProductById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.createProduct(productDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        try {
            return ResponseEntity.ok(productService.updateProduct(id, productDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productService.deleteProduct(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<Void> updateStock(@PathVariable Long id, @RequestParam int quantity) {
        try {
            boolean updated = productService.updateStock(id, quantity);
            if (updated) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
