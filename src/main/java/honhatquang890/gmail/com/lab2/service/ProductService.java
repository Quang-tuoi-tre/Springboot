package honhatquang890.gmail.com.lab2.service;

import honhatquang890.gmail.com.lab2.model.OrderDetail;
import honhatquang890.gmail.com.lab2.model.Product;
import honhatquang890.gmail.com.lab2.repository.OrderDetailRepository;
import honhatquang890.gmail.com.lab2.repository.OrderRepository;
import honhatquang890.gmail.com.lab2.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    private final OrderDetailRepository orderDetailRepository;

    private final OrderRepository orderRepository;
    // Retrieve all products from the database
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Retrieve a product by its id
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Add a new product to the database
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // Update an existing product
    public Product updateProduct(@NotNull Product product, MultipartFile imageProduct) {
        Product existingProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new IllegalStateException("Product with ID " +
                        product.getId() + " does not exist."));
        if (!imageProduct.isEmpty()) {
            // Delete existing image file if it exists
            if (existingProduct.getImage() != null && !existingProduct.getImage().isEmpty()) {
                Path existingImagePath = Paths.get("static/images", existingProduct.getImage());
                try {
                    Files.deleteIfExists(existingImagePath);
                } catch (IOException e) {
                    e.printStackTrace(); // Handle the exception appropriately
                }
            }
            try {
                Path dirImages = Paths.get("static/images");
                if (!Files.exists(dirImages)) {
                    Files.createDirectories(dirImages);
                }
                String newFileName = UUID.randomUUID() + "_" + imageProduct.getOriginalFilename();
                Path pathFileUpload = dirImages.resolve(newFileName);
                Files.copy(imageProduct.getInputStream(), pathFileUpload, StandardCopyOption.REPLACE_EXISTING);
                product.setImage(newFileName);
            } catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        } else {
            product.setImage(existingProduct.getImage());
        }
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setImage((product.getImage()));
        return productRepository.save(existingProduct);
    }

    // Delete a product by its id
    public void deleteProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Product with ID " + id + " does not exist."));
        // Get all order details related to the product
        List<OrderDetail> orderDetails = orderDetailRepository.findByProductId(id);
        // Delete all related order details and their corresponding orders if no other order details exist
        for (OrderDetail orderDetail : orderDetails) {
            Long orderId = orderDetail.getOrder().getId();
            orderDetailRepository.delete(orderDetail);
            if (orderDetailRepository.countByOrderId(orderId) == 0) {
                orderRepository.deleteById(orderId);
            }
        }
        if (product.getImage() != null && !product.getImage().isEmpty()) {
            Path imagePath = Paths.get("static/images", product.getImage());
            try {
                Files.deleteIfExists(imagePath);
            } catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }
        productRepository.deleteById(id);
    }

    //Tải hình ảnh
    public void updateImage(Product newProduct, MultipartFile imageProduct) {
        if (!imageProduct.isEmpty()) {
            try {
                Path dirImages = Paths.get("static/images");
                if (!Files.exists(dirImages)) {
                    Files.createDirectories(dirImages);
                }
                String newFileName = UUID.randomUUID() + "_" + imageProduct.getOriginalFilename();
                Path pathFileUpload = dirImages.resolve(newFileName);
                Files.copy(imageProduct.getInputStream(), pathFileUpload, StandardCopyOption.REPLACE_EXISTING);
                newProduct.setImage(newFileName);
            } catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }
    }
    public List<Product> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }
}
