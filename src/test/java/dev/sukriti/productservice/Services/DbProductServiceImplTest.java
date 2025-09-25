package dev.sukriti.productservice.Services;

import dev.sukriti.productservice.DTOs.ProductDto;
import dev.sukriti.productservice.Execptions.NotFoundException;
import dev.sukriti.productservice.Models.Category;
import dev.sukriti.productservice.Models.Product;
import dev.sukriti.productservice.Repository.CategoryRepository;
import dev.sukriti.productservice.Repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;


@Slf4j
@ExtendWith(MockitoExtension.class)
class DbProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private DbProductServiceImpl dbProductService;

    // ================== GET SINGLE PRODUCT ==================
    @Test
    void testGetSingleProductWhenProductExists() throws NotFoundException {
        log.info("Running testGetSingleProductWhenProductExists");

        Product product = new Product();
        product.setId(1L);
        product.setTitle("Iphone 17");
        product.setDescription("256 GB RAM LATEST");
        product.setImageUrl("https://www.iphone.com");
        product.setPrice(BigDecimal.valueOf(120000));

        when(productRepository.findProductById(1L)).thenReturn(Optional.of(product));

        Product returnedProduct = dbProductService.getSingleProduct(1L);
        assertNotNull(returnedProduct, "Returned product should not be null");
        assertEquals("Iphone 17", returnedProduct.getTitle());
        log.info("Returned product title: {}", returnedProduct.getTitle());

        verify(productRepository, times(1)).findProductById(1L);

        //AssertJ
        //Pick the data you want to validate first
        //Call the validations you want to do
        //
        assertThat(product.getPrice())
                .isEqualTo(BigDecimal.valueOf(120000))
                .isGreaterThan(BigDecimal.valueOf(100000))
                .isLessThan(BigDecimal.valueOf(10000))
                .isPositive();
    }




    @Test
    void testGetSingleProductWhenProductNotFound() {
        log.info("Running testGetSingleProductWhenProductNotFound");

        when(productRepository.findProductById(10000L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> dbProductService.getSingleProduct(10000L));
        verify(productRepository, times(1)).findProductById(10000L);
    }

    // ================== ADD NEW PRODUCT ==================
    @Test
    void testAddNewProductWhenCategoryExists() {
        log.info("Running testAddNewProductWhenCategoryExists");

        ProductDto productDto = new ProductDto();
        productDto.setTitle("MacBook Pro");
        productDto.setPrice(2500.0);
        productDto.setCategory("Laptops");
        productDto.setDescription("Apple Laptop");
        productDto.setImage("image.png");

        Category category = new Category();
        category.setId(10L);
        category.setName("Laptops");

        // Mock category exists
        when(categoryRepository.findByName("Laptops")).thenReturn(Optional.of(category));

        // Mock product insertion
        doNothing().when(productRepository).insertProduct(
                anyString(), anyDouble(), anyString(), anyLong(), anyString()
        );

        // Mock fetching last inserted product
        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setTitle("MacBook Pro");

        when(productRepository.findAllProducts()).thenReturn(List.of(savedProduct));

        Product result = dbProductService.addNewProduct(productDto);

        assertNotNull(result);
        assertEquals("MacBook Pro", result.getTitle());
        log.info("Added product title: {}", result.getTitle());

        verify(productRepository).insertProduct(
                eq("MacBook Pro"), eq(2500.0), eq("Apple Laptop"), eq(10L), eq("image.png")
        );
    }

    // ================== UPDATE PRODUCT ==================
    @Test
    void testUpdateProductWhenCategoryNotFound() throws NotFoundException {
        log.info("Running testUpdateProductWhenCategoryNotFound");

        // Input product to update
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Updated iPhone");
        product.setPrice(BigDecimal.valueOf(1200));
        product.setDescription("Iphone 13");
        product.setImageUrl(null); // optional

        // Mock category lookup → returns empty
        lenient().when(categoryRepository.findByName(anyString())).thenReturn(Optional.empty());

        // Mock the existing product in DB
        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setTitle("Updated iPhone");
        existingProduct.setPrice(BigDecimal.valueOf(1200));
        existingProduct.setDescription("Iphone 13");
        existingProduct.setImageUrl(null);

        // Spy on service to mock getSingleProduct
        DbProductServiceImpl spyService = spy(dbProductService);
        doReturn(existingProduct).when(spyService).getSingleProduct(1L);

        // Mock updateProduct call (void method)
        doNothing().when(productRepository).updateProduct(
                anyLong(), anyString(), any(BigDecimal.class), anyString(), any(), any()
        );

        // Call the service method
        Product result = spyService.updateProduct(1L, product);

        // Assertions
        assertNotNull(result);
        assertEquals("Updated iPhone", result.getTitle());
        assertEquals(BigDecimal.valueOf(1200), result.getPrice());
        assertEquals("Iphone 13", result.getDescription());

        log.info("Updated product title: {}", result.getTitle());

        // Verify repository update call
        verify(productRepository).updateProduct(
                eq(1L),
                eq("Updated iPhone"),
                eq(BigDecimal.valueOf(1200)),
                eq("Iphone 13"),
                isNull(), // categoryId
                isNull()  // imageUrl
        );
    }

    // ================== DELETE PRODUCT ==================
    @Test
    void testDeleteProductWhenProductExists() {
        log.info("Running testDeleteProductWhenProductExists");

        Product product = new Product();
        product.setId(5L);

        when(productRepository.findProductById(5L)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).deleteProductById(5L);

        boolean result = dbProductService.deleteProduct(5L);

        assertTrue(result);
        log.info("Delete result: {}", true);
        log.info("Delete result: {}", true);

        verify(productRepository).deleteProductById(5L);
    }

    @Test
    void testDeleteProductWhenProductDoesNotExist() {
        log.info("Running testDeleteProductWhenProductDoesNotExist");

        when(productRepository.findProductById(100L)).thenReturn(Optional.empty());

        boolean result = dbProductService.deleteProduct(100L);

        assertFalse(result);
        log.info("Delete result for non-existent product: {}", false);

        verify(productRepository, never()).deleteProductById(anyLong());
    }
}