package honhatquang890.gmail.com.lab2.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Name is required")
    private String name;
    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be positive")
    private int price;
    @NotNull(message = "Description is required")
    private String description;
    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull(message = "Category is required")
    @Valid
    private Category category;
    @Length(min= 0,max = 200, message = "Tên hình ảnh không quá 200 kí tự")
    private String image;

    public @Length(min = 0, max = 200, message = "Tên hình ảnh không quá 200 kí tự") String getImage() {
        return image;
    }

    public void setImage(@Length(min = 0, max = 200, message = "Tên hình ảnh không quá 200 kí tự") String image) {
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
