package com.springboot.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import com.springboot.ecommerce.model.ProductCategory;
import com.springboot.ecommerce.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    ProductCategoryRepository productCategoryRepo;

    @Override
    public ProductCategory createProductCategory(ProductCategory productCategory) {
        return productCategoryRepo.save(productCategory);
    }

    @Override
    public ProductCategory editProductCategory(ProductCategory productCategory) {
        productCategoryRepo.save(productCategory);
        return productCategory;
    }

    @Override
    public void removeCategoryById(int id) {
        productCategoryRepo.deleteById(new Double(id).longValue());
    }

    @Override
    public List<ProductCategory> getCategoryList(String lang) {
        return productCategoryRepo.findAllByLang(lang);
    }

    @Override
    public ProductCategory findCategoryById(int id) {
        return productCategoryRepo.findById(new Double(id).longValue()).get();
    }
    
}
