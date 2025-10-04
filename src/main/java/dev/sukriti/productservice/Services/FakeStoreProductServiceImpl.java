package dev.sukriti.productservice.Services;

import dev.sukriti.productservice.Clients.FakeStore.FakeStoreApi;
import dev.sukriti.productservice.Clients.FakeStore.FakeStoreProductDto;
import dev.sukriti.productservice.DTOs.ProductDto;
import dev.sukriti.productservice.Models.Category;
import dev.sukriti.productservice.Models.Product;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

@Service
@Qualifier("fakeStoreProductService")
@Primary
public class FakeStoreProductServiceImpl implements ProductService{

    //Allow to call 3rd Party Apis
    private final RestTemplateBuilder restTemplateBuilder;
    private final FakeStoreApi fakeStoreApi;
    private final Map<Long,Object> fakeStoreProducts = new HashMap<>();
    private RedisTemplate<Long,Object> redisTemplate;

    public FakeStoreProductServiceImpl(RestTemplateBuilder restTemplateBuilder, FakeStoreApi fakeStoreApi, RedisTemplate<Long, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.restTemplateBuilder = restTemplateBuilder;
        this.fakeStoreApi = fakeStoreApi;
    }

    private <T> ResponseEntity<T> requestForEntity(HttpMethod httpMethod, String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.requestFactory(
                HttpComponentsClientHttpRequestFactory.class
                ).build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }

    private Product convertFakeStoreProductDtoToProduct(FakeStoreProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setPrice(BigDecimal.valueOf(productDto.getPrice()));
        //Category is object in Product DTO
        Category category = new Category();
        category.setName(productDto.getCategory());
        product.setCategory(category);
        product.setDescription(productDto.getDescription());
        product.setImageUrl(product.getImageUrl());
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        List<FakeStoreProductDto> fakeStoreProductDtos = fakeStoreApi.getAllProducts();
        List<Product> answer = new ArrayList<>();
        for (FakeStoreProductDto productDto : fakeStoreProductDtos) {
            answer.add(convertFakeStoreProductDtoToProduct(productDto));
        }
        return answer;
    }

    // It will return product object with details of the fetched product
    // The ID of category may be null but the name of category shall be
    // correct.

    @Override
    public Product getSingleProduct(Long productId) {
        FakeStoreProductDto fakeStoreProductDto;
        RestTemplate restTemplate = restTemplateBuilder.build();
        //whatever JSON you are getting from url try to convert it into
        //ProductDTO class because it is same as model at URL.
        if (redisTemplate.opsForHash().get("PRODUCTS",productId)){
        if (fakeStoreProducts.containsKey(productId)) {
            return Optional.of(convertFakeStoreProductDtoToProduct(Fa))
        }
        }
            ResponseEntity<FakeStoreProductDto> response = restTemplate.getForEntity(
                "https://fakestoreapi.com/products/{id}",
                FakeStoreProductDto.class,
                productId
                );
        //Url, return type, parameter in url

        FakeStoreProductDto productDto = response.getBody();
        assert productDto != null;
        return convertFakeStoreProductDtoToProduct(productDto);
    }

    @Override
    public Product addNewProduct(ProductDto product) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = restTemplate.postForEntity(
                "https://fakestoreapi.com/products",
                product,
                FakeStoreProductDto.class
        );
        FakeStoreProductDto productDto = response.getBody();
        assert productDto != null;
        return convertFakeStoreProductDtoToProduct(productDto);
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setImage(product.getImageUrl());
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setPrice(product.getPrice().doubleValue());
        fakeStoreProductDto.setCategory(product.getCategory().getName());

        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = requestForEntity(
                HttpMethod.PATCH,
                "https://fakestoreapi.com/products/{id}",
                fakeStoreProductDto,
                FakeStoreProductDto.class,
                productId
        );
        assert fakeStoreProductDtoResponseEntity.getBody() != null;
        return convertFakeStoreProductDtoToProduct(fakeStoreProductDtoResponseEntity.getBody());
    }

    @Override
    public Product replaceProduct(Long productId, Product product) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setImage(product.getImageUrl());
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setPrice(product.getPrice().doubleValue());
        fakeStoreProductDto.setCategory(product.getCategory().getName());

        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = requestForEntity(
                HttpMethod.PUT,
                "https://fakestoreapi.com/products/{id}",
                fakeStoreProductDto,
                FakeStoreProductDto.class,
                productId
        );
        assert fakeStoreProductDtoResponseEntity.getBody() != null;
        return convertFakeStoreProductDtoToProduct(fakeStoreProductDtoResponseEntity.getBody());
    }

    @Override
    public boolean deleteProduct(Long productId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        return false;
    }

}
