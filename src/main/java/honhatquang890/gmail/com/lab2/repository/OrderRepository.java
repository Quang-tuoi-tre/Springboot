package honhatquang890.gmail.com.lab2.repository;

import honhatquang890.gmail.com.lab2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderRepository extends JpaRepository<Product, Long> {
}

