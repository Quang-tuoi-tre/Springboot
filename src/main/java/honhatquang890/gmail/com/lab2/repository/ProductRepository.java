package honhatquang890.gmail.com.lab2.repository;

import honhatquang890.gmail.com.lab2.model.Category;
import honhatquang890.gmail.com.lab2.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
   /* @Transactional
    @Modifying
    @Query("DELETE FROM Product p WHERE p.category.id = :categoryId")
    void deleteByCategoryId(Long categoryId);*/
    List<Product> findByCategory(Category category);
    List<Product> findByNameContainingIgnoreCase(String keyword);

}
