package dev.sukriti.productservice.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sukriti.productservice.Execptions.NotFoundException;
import dev.sukriti.productservice.Models.Category;
import dev.sukriti.productservice.Models.Product;
import dev.sukriti.productservice.Repository.CategoryRepository;
import dev.sukriti.productservice.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("dbCategoryService")
public class DbCategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    public DbCategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository, ObjectMapper objectMapper) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }


    @Override
    public String getAllCategories() throws NotFoundException {
        List<Category> categories = categoryRepository.findAll();
        try {
            return objectMapper.writeValueAsString(categories);
        } catch (JsonProcessingException e) {
            throw new NotFoundException("Error serializing categories");
        }
    }


    @Override
    public String getProductsInCategory(Long categoryId) throws NotFoundException {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category not found with id " + categoryId));

        List<Product> products = category.getProducts();

        try {
            return objectMapper.writeValueAsString(products);
        } catch (JsonProcessingException e) {
            throw new NotFoundException("Error serializing products in category " + categoryId);
        }
    }

    @Override
    public String addNewCategory(String name, String description) throws NotFoundException {
        Category category = categoryRepository.findByName(name)
                .orElseGet(() -> {
                    categoryRepository.insertCategory(name, description);
                    try {
                        return categoryRepository.findByName(name)
                                .orElseThrow(() -> new NotFoundException("Failed to insert category " + name));
                    } catch (NotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });

        try {
            return objectMapper.writeValueAsString(category);
        } catch (JsonProcessingException e) {
            throw new NotFoundException("Error serializing category " + name);
        }
    }
}
