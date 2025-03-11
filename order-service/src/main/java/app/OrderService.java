package app;

import app.ProductServiceClient;
import app.model.*;
import app.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductServiceClient productServiceClient;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductServiceClient productServiceClient) {
        this.orderRepository = orderRepository;
        this.productServiceClient = productServiceClient;
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    public List<OrderDTO> getOrdersByCustomerEmail(String email) {
        return orderRepository.findByCustomerEmail(email)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderDTO createOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setCustomerName(orderRequest.getCustomerName());
        order.setCustomerEmail(orderRequest.getCustomerEmail());
        order.setShippingAddress(orderRequest.getShippingAddress());
        order.setStatus(OrderStatus.PENDING);

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : orderRequest.getItems()) {
            // Get product details from product service
            ProductDTO product = productServiceClient.getProductById(itemRequest.getProductId())
                    .block(); // Blocking call for simplicity

            if (product == null) {
                throw new RuntimeException("Product not found with id: " + itemRequest.getProductId());
            }

            // Check if there's enough stock
            if (product.getStock() < itemRequest.getQuantity()) {
                throw new RuntimeException("Not enough stock for product: " + product.getName());
            }

            // Create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setProductPrice(product.getPrice());
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.calculateSubtotal();

            order.addItem(orderItem);
            totalAmount = totalAmount.add(orderItem.getSubtotal());

            // Update product stock
            productServiceClient.updateProductStock(product.getId(), itemRequest.getQuantity())
                    .block(); // Blocking call for simplicity
        }

        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);

        return convertToDTO(savedOrder);
    }

    @Transactional
    public OrderDTO updateOrderStatus(Long id, OrderStatus newStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        order.setStatus(newStatus);
        Order updatedOrder = orderRepository.save(order);

        return convertToDTO(updatedOrder);
    }

    private OrderDTO convertToDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setCustomerName(order.getCustomerName());
        orderDTO.setCustomerEmail(order.getCustomerEmail());
        orderDTO.setShippingAddress(order.getShippingAddress());
        orderDTO.setTotalAmount(order.getTotalAmount());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setCreatedAt(order.getCreatedAt());

        List<OrderItemDTO> itemDTOs = order.getItems().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        orderDTO.setItems(itemDTOs);

        return orderDTO;
    }

    private OrderItemDTO convertToDTO(OrderItem orderItem) {
        OrderItemDTO itemDTO = new OrderItemDTO();
        itemDTO.setId(orderItem.getId());
        itemDTO.setProductId(orderItem.getProductId());
        itemDTO.setProductName(orderItem.getProductName());
        itemDTO.setProductPrice(orderItem.getProductPrice());
        itemDTO.setQuantity(orderItem.getQuantity());
        itemDTO.setSubtotal(orderItem.getSubtotal());
        return itemDTO;
    }
}