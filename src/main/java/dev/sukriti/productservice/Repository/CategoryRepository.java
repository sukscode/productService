package dev.sukriti.productservice.Repository;

import dev.sukriti.productservice.Constants.Queries;
import dev.sukriti.productservice.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CategoryRepository  extends JpaRepository<Category, Long> {
    @Query(value = Queries.GET_CATEGORY_BY_NAME, nativeQuery = true)
    Optional<Category> findByName(@Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = Queries.INSERT_CATEGORY, nativeQuery = true)
    void insertCategory(@Param("name") String name,
                        @Param("description") String description);
}
