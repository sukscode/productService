package dev.sukriti.productservice.Repository;

import dev.sukriti.productservice.Constants.Queries;
import dev.sukriti.productservice.Models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = Queries.GET_ALL_PRODUCTS, nativeQuery = true)
    List<Product> findAllProducts();

    @Query(value = Queries.GET_PRODUCT_BY_ID, nativeQuery = true)
    Optional<Product> findProductById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = Queries.DELETE_PRODUCT_BY_ID, nativeQuery = true)
    void deleteProductById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = Queries.INSERT_PRODUCT, nativeQuery = true)
    void insertProduct(@Param("title") String title,
                       @Param("price") double price,
                       @Param("description") String description,
                       @Param("categoryId") Long categoryId,
                       @Param("imageUrl") String imageUrl);

    @Modifying
    @Transactional
    @Query(value = Queries.UPDATE_PRODUCT, nativeQuery = true)
    void updateProduct(@Param("id") Long id,
                       @Param("title") String title,
                       @Param("price") BigDecimal price,
                       @Param("description") String description,
                       @Param("categoryId") Long categoryId,
                       @Param("imageUrl") String imageUrl);

    @Modifying
    @Transactional
    @Query(value = Queries.REPLACE_PRODUCT, nativeQuery = true)
    void replaceProduct(@Param("id") Long id,
                        @Param("title") String title,
                        @Param("price") BigDecimal price,
                        @Param("description") String description,
                        @Param("categoryId") Long categoryId,
                        @Param("imageUrl") String imageUrl);


    Page<Product> findAll(Pageable pageable);
}
