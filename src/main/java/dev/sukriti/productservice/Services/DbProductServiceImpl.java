package dev.sukriti.productservice.Services;

import dev.sukriti.productservice.DTOs.ProductDto;
import dev.sukriti.productservice.Execptions.NotFoundException;
import dev.sukriti.productservice.Models.Category;
import dev.sukriti.productservice.Models.Product;
import dev.sukriti.productservice.Repository.CategoryRepository;
import dev.sukriti.productservice.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("dbProductService")
//@Primary
public class DbProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public DbProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAllProducts();
    }

    @Override
    public Product getSingleProduct(Long productId) throws NotFoundException{
        return productRepository.findProductById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with id " + productId));
    }

    @Override
    public Product addNewProduct(ProductDto product) {

        //Check if category exists
        Long categoryId = categoryRepository.findByName(product.getCategory())
                .map(Category::getId)
                .orElseGet(() -> {
                    //If not found → insert new category
                    categoryRepository.insertCategory(product.getCategory(), "Auto-created");
                    //Fetch category again to get its generated ID
                    return categoryRepository.findByName(product.getCategory()).orElseThrow().getId();
                });

        //Insert new product into DB
        productRepository.insertProduct(product.getTitle(),
                product.getPrice(),
                product.getDescription(),
                categoryId,
                product.getImage());

        //Fetch the last inserted product (using "get last element" trick)
        return productRepository.findAllProducts().stream()
                .reduce((first, second) -> second) //Gives last element
                .orElseThrow();
    }

    @Override
    public Product updateProduct(Long productId, Product product) throws NotFoundException {

        //Setting category id to null
        Long categoryId = null;

        //If product has a category and a name, try to find it in DB
        if (product.getCategory() != null && product.getCategory().getName() != null) {
            categoryId = categoryRepository.findByName(product.getCategory().getName())
                    .map(Category::getId)   // found → get id
                    .orElse(null);          // not found → keep null
        }

        //Update product in DB with possible categoryId
        productRepository.updateProduct(productId,
                product.getTitle(),
                product.getPrice(),
                product.getDescription(),
                categoryId,
                product.getImageUrl());

        //Fetch and return updated product
        return getSingleProduct(productId);
    }

    @Override
    public Product replaceProduct(Long productId, Product product) throws NotFoundException{
        Long categoryId = null;

        // If product has category and name, try fetching its ID from DB
        if (product.getCategory() != null && product.getCategory().getName() != null) {
            categoryId = categoryRepository.findByName(product.getCategory().getName())
                    .map(Category::getId)//found -> get id
                    .orElse(null);  // if category not found, keep null
        }

        // Replace existing product details with new ones
        productRepository.replaceProduct(productId,
                product.getTitle(),
                product.getPrice(),
                product.getDescription(),
                categoryId,
                product.getImageUrl());

        // Return the updated product from DB
        return getSingleProduct(productId);
    }

    @Override
    public boolean deleteProduct(Long productId){
        // First check if product exists in DB
        if (productRepository.findProductById(productId).isEmpty()) {
            // Product not found → cannot delete
            return false;
        } else {
            // Product exists → delete it
            productRepository.deleteProductById(productId);
            return true;
        }
    }
}
