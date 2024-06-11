package honhatquang890.gmail.com.lab2.controller;

import honhatquang890.gmail.com.lab2.model.CartItem;
import honhatquang890.gmail.com.lab2.model.Order;
import honhatquang890.gmail.com.lab2.model.OrderDetail;
import honhatquang890.gmail.com.lab2.service.CartService;
import honhatquang890.gmail.com.lab2.service.OrderService;
import org.springframework.stereotype.Controller;

import honhatquang890.gmail.com.lab2.model.Product;
import honhatquang890.gmail.com.lab2.service.CategoryService;
import honhatquang890.gmail.com.lab2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;

    @GetMapping("/checkout")
    public String checkout(Model model) {
        model.addAttribute("order", new Order());
        return "/cart/checkout";
    }
    @PostMapping("/submit")
    public String submitOrder(@Valid @ModelAttribute("order") Order order, BindingResult result) {
        List<CartItem> cartItems = cartService.getCartItems();
        if (cartItems.isEmpty()) {
            return "redirect:/cart"; // Redirect if cart is empty
        }

        if (result.hasErrors()) {
            return "/cart/checkout";
        }

        Order savedOrder = orderService.createOrder(order, cartItems);
        return "redirect:/order/confirmation?orderId=" + savedOrder.getId();
    }
    @GetMapping("/confirmation")
    public String orderConfirmation(@RequestParam("orderId") Long orderId, Model model) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        return "cart/order-confirmation";
    }
    @GetMapping("/orderdetail")
    public String showOrderDetails(Model model) {
        List<OrderDetail> orderDetails = orderService.getAllOrderDetails();
        model.addAttribute("orderDetails", orderDetails);
        return "cart/order-details";
    }
    @GetMapping("/delete/{id}")
    public String deleteOrderDetail(@PathVariable Long id) {
        orderService.deleteOrderDetailById(id);
        return "redirect:/order/orderdetail";
    }

}
