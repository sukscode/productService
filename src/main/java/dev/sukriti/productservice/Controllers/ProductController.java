package dev.sukriti.productservice.Controllers;

import dev.sukriti.productservice.Authentication.AuthenticationClient;
import dev.sukriti.productservice.DTOs.*;
import dev.sukriti.productservice.Execptions.NotFoundException;
import dev.sukriti.productservice.Models.Category;
import dev.sukriti.productservice.Models.Product;
import dev.sukriti.productservice.Repository.ProductRepository;
import dev.sukriti.productservice.Services.ProductService;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final AuthenticationClient authenticationClient;


    //For switching Service
    public ProductController(ProductService productService, ProductRepository productRepository, AuthenticationClient authenticationClient) {
        this.productRepository = productRepository;
        this.productService = productService;
        this.authenticationClient = authenticationClient;
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<Product>> getProducts(@RequestBody GetProductsRequestDto request) {
        return ResponseEntity.of(Optional.ofNullable(productService.getProducts(
                request.getNumberOfProducts(),
                request.getOffset()
        )));

    }
    // Make only admins be able to access all products
    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts(@Nullable @RequestHeader("AUTH_TOKEN") String token,
                                                        @Nullable @RequestHeader("USER_ID") Long userId) {
        // check if token exists
//        if (token == null || userId == null) {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//
//        ValidatetokenResponseDto response = authenticationClient.validate(token, userId);
//
//        // check if token is valid
//        if (response.getSessionStatus().equals(SessionStatus.INVALID)) {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//
//        // validate token
//        // RestTemplate rt = new RestTemplate();
//        //  rt.get("localhost:9090/auth/validate?)
//
//        // check if user has permissions
//        boolean isUserAdmin = false;
//        for (Role role: response.getUserDto().getRoles()) {
//            if (role.getName().equals("ADMIN")) {
//                isUserAdmin = true;
//            }
//        }
//
//        if (!isUserAdmin) {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }

        List<Product> products = productService.getAllProducts();

//        products.get(0).setPrice(100); /// Bug induced in my code
        return new ResponseEntity<>(products, HttpStatus.OK);
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
