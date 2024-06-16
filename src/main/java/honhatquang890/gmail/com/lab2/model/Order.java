package honhatquang890.gmail.com.lab2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*@NotBlank(message = "Customer name is required")
    private String customerName;*/

    @NotBlank(message = "Delivery address is required")
    private String deliveryAddress;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @Email(message = "Email should be valid")
    private String email;

    private String notes;

    @NotBlank(message = "Payment method is required")
    private String paymentMethod;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;
}
