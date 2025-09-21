package dev.sukriti.productservice.Clients.FakeStore;

import dev.sukriti.productservice.DTOs.ProductDto;
import dev.sukriti.productservice.Execptions.NotFoundException;
import dev.sukriti.productservice.Models.Product;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class FakeStoreApi {
    private RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

    public FakeStoreApi(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public List<FakeStoreProductDto> getAllProducts(){
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto[]> response = restTemplate.getForEntity(
                "https://fakestoreapi.com/products",
                FakeStoreProductDto[].class
        );
        return Arrays.asList(response.getBody());
    }

    Optional<FakeStoreProductDto> getSingleProduct(Long productId) throws NotFoundException {
        return Optional.empty();
    }

    FakeStoreProductDto addNewProduct(ProductDto productDto){
        return null;
    }

    FakeStoreProductDto replaceProduct(Long productId, Product product) {
        return null;
    }

    FakeStoreProductDto deleteProduct(Long productId) {
        return null;
    }
}
