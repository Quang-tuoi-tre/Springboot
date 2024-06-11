package honhatquang890.gmail.com.lab2.repository;

import honhatquang890.gmail.com.lab2.model.OrderDetail;
import honhatquang890.gmail.com.lab2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    void deleteByProduct(Product product);
    List<OrderDetail> findByProductId(Long productId);
    int countByOrderId(Long orderId);

}

