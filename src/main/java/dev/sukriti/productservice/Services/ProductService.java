package dev.sukriti.productservice.Services;

import dev.sukriti.productservice.DTOs.ProductDto;
import dev.sukriti.productservice.Execptions.NotFoundException;
import dev.sukriti.productservice.Models.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product getSingleProduct(Long productId) throws NotFoundException;

    Product addNewProduct(ProductDto product);

    /*
    Product Object only have those fields which needs to be updated
    everything else will be null
    {
    Update Product at I'd 123
    name: Iphone17
    }
     */
    Product updateProduct(Long productId, Product product) throws NotFoundException;

    Product replaceProduct(Long productId, Product product) throws NotFoundException;

    boolean deleteProduct(Long productId);

    Page<Product> getProducts(int numberOfProducts, int offset);
}
