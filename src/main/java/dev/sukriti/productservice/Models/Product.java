package dev.sukriti.productservice.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product extends BaseModel {
    private String title;
    private BigDecimal price;
    private String description;
    @ManyToOne
    @JsonBackReference  //Allows serialization of products, but stops back loop
    private Category category;
    private String imageUrl;

}
