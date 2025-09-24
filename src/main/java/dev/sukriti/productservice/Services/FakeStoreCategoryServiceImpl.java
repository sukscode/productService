package dev.sukriti.productservice.Services;

import dev.sukriti.productservice.Execptions.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FakeStoreCategoryServiceImpl implements CategoryService {

    @Override
    public String getAllCategories(){
        return null;
    }

    @Override
    public String getProductsInCategory(Long categoryId){
        return null;
    }

    @Override
    public String addNewCategory(String name, String description) throws NotFoundException {
        return "";
    }

}
