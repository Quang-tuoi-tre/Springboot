package honhatquang890.gmail.com.lab2.repository;

import honhatquang890.gmail.com.lab2.model.Category;
import honhatquang890.gmail.com.lab2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
