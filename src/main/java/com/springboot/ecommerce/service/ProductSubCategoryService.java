package com.springboot.ecommerce.service;

import com.springboot.ecommerce.model.ProductSubCategory;
import java.util.List;

public interface ProductSubCategoryService {
    public ProductSubCategory createSubCategory(ProductSubCategory subCategory);
    public ProductSubCategory editSubCategory(ProductSubCategory subCategory);
    public void removeSubCategoryById(int id);
    public List<ProductSubCategory> getSubCategoryList(String lang);
    public ProductSubCategory findSubCategoryById(int id);
}
