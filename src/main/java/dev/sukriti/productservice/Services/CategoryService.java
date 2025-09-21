package dev.sukriti.productservice.Services;

public interface CategoryService {
    String getAllCategories();
    String getProductsInCategory(Long categoryId);
}
