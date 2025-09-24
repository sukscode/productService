package dev.sukriti.productservice.Controllers;


import dev.sukriti.productservice.Execptions.NotFoundException;
import dev.sukriti.productservice.Services.CategoryService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products/categories")
public class CategoryController {
    private final CategoryService categoryService;

    CategoryController(@Qualifier("dbCategoryService") CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public String getAllCategories() throws NotFoundException {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{categoryId}")
    public String getProductsInCategory(@PathVariable Long categoryId) throws Exception {
        return categoryService.getProductsInCategory(categoryId);
    }

    @PostMapping()
    public String addCategory(@RequestParam String name,
                                @RequestParam(required = false, defaultValue = "Auto-created") String description) throws NotFoundException {
        return categoryService.addNewCategory(name, description);
    }

}
