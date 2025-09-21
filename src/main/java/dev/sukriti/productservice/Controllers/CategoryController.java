package dev.sukriti.productservice.Controllers;


import dev.sukriti.productservice.Services.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products/categories")
public class CategoryController {
    private CategoryService catergoryService;

    CategoryController(CategoryService catergoryService) {
        this.catergoryService = catergoryService;
    }

    @GetMapping()
    public String getAllCategories(){
        return "Getting all categories";
    }

    @GetMapping("/{categoryId}")
    public String getProductsInCategory(@PathVariable("categoryId") Long categoryId){
        return "Getting product in category: " + categoryId;
    }

}
