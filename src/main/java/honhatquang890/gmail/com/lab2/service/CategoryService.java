package honhatquang890.gmail.com.lab2.service;


import honhatquang890.gmail.com.lab2.model.Category;
import honhatquang890.gmail.com.lab2.model.Product;
import honhatquang890.gmail.com.lab2.repository.CategoryRepository;
import honhatquang890.gmail.com.lab2.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.constraints.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;



@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
        private final CategoryRepository categoryRepository;

        private final ProductRepository productRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }


    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }


    public void addCategory(Category category) {
        categoryRepository.save(category);
    }


    public void updateCategory(@NotNull Category category) {
        Category existingCategory = categoryRepository.findById(category.getId())
                .orElseThrow(() -> new IllegalStateException("Category with ID " + category.getId() + " does not exist."));
        existingCategory.setName(category.getName());
        categoryRepository.save(existingCategory);
    }

    /*public void deleteCategoryById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new IllegalStateException("Category with ID " + id + " does not exist.");
        }
        categoryRepository.deleteById(id);
    }*/
    //Xóa cả product và Cate
   /* public void deleteCategoryAndProducts(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new IllegalStateException("Category with ID " + categoryId + " does not exist.");
        }
        productRepository.deleteByCategoryId(categoryId);
        categoryRepository.deleteById(categoryId);
    }*/

    public void deleteCategoryAndProducts(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalStateException("Category with ID " + categoryId + " does not exist."));

        // Retrieve all products associated with the category
        List<Product> products = productRepository.findByCategory(category);

        // Delete each product and its associated image
        for (Product product : products) {
            // Delete the image file if it exists
            if (product.getImage() != null && !product.getImage().isEmpty()) {
                Path imagePath = Paths.get("static/images", product.getImage());
                try {
                    Files.deleteIfExists(imagePath);
                } catch (IOException e) {
                    e.printStackTrace(); // Handle the exception appropriately
                }
            }
            productRepository.delete(product);
        }

        // Delete the category itself
        categoryRepository.delete(category);
    }
}
