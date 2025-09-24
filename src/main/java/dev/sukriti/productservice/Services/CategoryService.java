package dev.sukriti.productservice.Services;

import dev.sukriti.productservice.Execptions.NotFoundException;

public interface CategoryService{
    String getAllCategories() throws NotFoundException;
    String getProductsInCategory(Long categoryId) throws  Exception;
    String addNewCategory(String name, String description) throws NotFoundException;
}
