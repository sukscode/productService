package dev.sukriti.productservice.Repository;

import dev.sukriti.productservice.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product save(Product product);

}
