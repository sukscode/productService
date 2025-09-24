package dev.sukriti.productservice.Controllers;

import dev.sukriti.productservice.DTOs.GetSingleProductResponseDto;
import dev.sukriti.productservice.DTOs.ProductDto;
import dev.sukriti.productservice.Execptions.NotFoundException;
import dev.sukriti.productservice.Models.Category;
import dev.sukriti.productservice.Models.Product;
import dev.sukriti.productservice.Services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    //For switching Service
    public ProductController(@Qualifier("dbProductService") ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public GetSingleProductResponseDto getSingleProduct(@PathVariable("productId") Long productId) throws NotFoundException{
        GetSingleProductResponseDto responseDto = new GetSingleProductResponseDto();
        responseDto.setProduct(
                productService.getSingleProduct(productId)
        );
        Optional<Product> productOptional = Optional.ofNullable(productService.getSingleProduct(productId));

        if (productOptional.isEmpty()) {
            throw new NotFoundException("Product not found with id " + productId);
        }
        return responseDto;
    }

    @PostMapping()
    public ResponseEntity<Product> addNewProduct(@RequestBody ProductDto product) {
        Product newProduct = productService.addNewProduct(
                product);
        ResponseEntity<Product> responseEntity = new ResponseEntity<>(newProduct,HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping("/{productId}")
    public Product updateProduct(@PathVariable("productId") Long productId, @RequestBody ProductDto productDto) throws NotFoundException {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setCategory(new Category());
        product.setTitle(productDto.getTitle());
        product.setPrice(BigDecimal.valueOf(productDto.getPrice()));
        product.setDescription(productDto.getDescription());
        return productService.updateProduct(productId,product);
    }

    @PutMapping("/{productId}")
    public Product replaceProduct(@PathVariable("productId") Long productId, @RequestBody ProductDto productDto) throws NotFoundException {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setCategory(new Category());
        product.setTitle(productDto.getTitle());
        product.setPrice(BigDecimal.valueOf(productDto.getPrice()));
        product.setDescription(productDto.getDescription());
        return productService.replaceProduct(productId,product);
    }

    @DeleteMapping("/{productId}")
    public String deleteProduct(@PathVariable("productId") Long productId) {
        return "Deleted product with id " + productId;
    }
}
