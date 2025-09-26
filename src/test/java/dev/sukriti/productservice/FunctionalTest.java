package dev.sukriti.productservice;

import dev.sukriti.productservice.Controllers.ProductController;
import dev.sukriti.productservice.Models.Product;
import dev.sukriti.productservice.Services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//Allow to test API
@WebMvcTest(ProductController.class)
public class FunctionalTest {

    //Use to send request
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    @Qualifier("dbProductService")
    private ProductService productService;  // DbProductServiceImpl mocked

    @Test
    void testGetAllProducts() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("iPhone 17");

        when(productService.getAllProducts()).thenReturn(List.of(product));

        mockMvc.perform(get("/products")
                        .header("AUTH_TOKEN", "dummy-token"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("iPhone 17"));
    }
}
