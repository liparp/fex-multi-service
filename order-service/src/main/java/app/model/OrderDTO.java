package app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private String customerName;
    private String customerEmail;
    private String shippingAddress;
    private List<OrderItemDTO> items = new ArrayList<>();
    private BigDecimal totalAmount;
    private OrderStatus status;
    private LocalDateTime createdAt;
}