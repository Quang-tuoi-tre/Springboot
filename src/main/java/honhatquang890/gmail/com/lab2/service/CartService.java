package honhatquang890.gmail.com.lab2.service;

import honhatquang890.gmail.com.lab2.model.CartItem;
import honhatquang890.gmail.com.lab2.model.Product;
import honhatquang890.gmail.com.lab2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
@SessionScope
public class CartService {


    private List<CartItem> cartItems = new ArrayList<>();
    @Autowired
    private ProductRepository productRepository;
    public void addToCart(Long productId, int quantity) {
        Product product = productRepository.findById(productId)

                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
                        cartItems.add(new CartItem(product, quantity));
    }
    public List<CartItem> getCartItems() {
        // Filter out cart items for products that no longer exist
        Iterator<CartItem> iterator = cartItems.iterator();
        while (iterator.hasNext()) {
            CartItem item = iterator.next();
            if (!productRepository.existsById(item.getProduct().getId())) {
                iterator.remove();
            }
        }
        return cartItems;
    }
    public void removeFromCart(Long productId) {
        cartItems.removeIf(item -> item.getProduct().getId().equals(productId));
    }
    public void clearCart() {
        cartItems.clear();
    }
}
