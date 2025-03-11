package app;

import jakarta.validation.Valid;
import app.model.OrderDTO;
import app.model.OrderRequest;
import app.model.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(orderService.getOrderById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/customer/{email}")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomerEmail(@PathVariable String email) {
        return ResponseEntity.ok(orderService.getOrdersByCustomerEmail(email));
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        try {
            return new ResponseEntity<>(orderService.createOrder(orderRequest), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {
        try {
            return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
