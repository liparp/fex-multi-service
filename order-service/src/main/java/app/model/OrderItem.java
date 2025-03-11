package app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private String productName;
    private BigDecimal productPrice;
    private int quantity;
    private BigDecimal subtotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    @PrePersist
    @PreUpdate
    public void calculateSubtotal() {
        this.subtotal = this.productPrice.multiply(BigDecimal.valueOf(this.quantity));
    }
}