package dev.sukriti.productservice.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseModel {
    private String name;
    private String description;
    @OneToMany(mappedBy = "category")
    @JsonManagedReference // Prevent infinite recursion
    private List<Product> products;
}
