package honhatquang890.gmail.com.lab2.service;

import honhatquang890.gmail.com.lab2.model.CartItem;
import honhatquang890.gmail.com.lab2.model.Order;
import honhatquang890.gmail.com.lab2.model.OrderDetail;
import honhatquang890.gmail.com.lab2.model.Product;
import honhatquang890.gmail.com.lab2.repository.OrderDetailRepository;
import honhatquang890.gmail.com.lab2.repository.OrderRepository;
import honhatquang890.gmail.com.lab2.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private CartService cartService; // Assuming you have a CartService
    @Transactional
    public Order createOrder(Order order, List<CartItem> cartItems) {
        // Save the order with customer details and other info
        Order savedOrder = orderRepository.save(order);

        // Save order details
        for (CartItem item : cartItems) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(savedOrder);
            detail.setProduct(item.getProduct());
            detail.setQuantity(item.getQuantity());
            orderDetailRepository.save(detail);
        }

        // Optionally clear the cart after order placement
        cartService.clearCart();

        return savedOrder;
    }
    public Order findById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
    }
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }
    public void deleteOrderDetailById(Long id) {
        orderDetailRepository.deleteById(id);
    }
}
