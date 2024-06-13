package honhatquang890.gmail.com.lab2.repository;

import honhatquang890.gmail.com.lab2.model.OrderDetail;
import honhatquang890.gmail.com.lab2.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByProductId(Long productId);

    @Modifying
    @Transactional
    @Query("DELETE FROM OrderDetail od WHERE od.product.id = :productId")
    void deleteByProductId(Long productId);
    int countByOrderId(Long orderId);

}

