package dev.sukriti.productservice.Services;

import dev.sukriti.productservice.DTOs.ProductDto;
import dev.sukriti.productservice.Models.Product;
import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product getSingleProduct(Long productId);
    Product addNewProduct(ProductDto product);

    /*
    Product Object only have those fields which needs to be updated
    everything else will be null
    {
    Update Product at I'd 123
    name: Iphone17
    }
     */
    Product updateProduct(Long productId, Product product);

    Product replaceProduct(Long productId, Product product);

    boolean deleteProduct(Long productId);
}
