package com.springboot.ecommerce.service;


import com.springboot.ecommerce.model.ProductSubCategory;
import com.springboot.ecommerce.repository.ProductSubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductSubCategoryServiceImpl implements ProductSubCategoryService{

    @Autowired
    ProductSubCategoryRepository subCategoryRepo;

    @Override
    public ProductSubCategory createSubCategory(ProductSubCategory subCategory) {
        return subCategoryRepo.save(subCategory);
    }

    @Override
    public ProductSubCategory editSubCategory(ProductSubCategory subCategory) {
        return subCategoryRepo.save(subCategory);
    }

    @Override
    public void removeSubCategoryById(int id) {
        subCategoryRepo.deleteById(new Double(id).longValue());
    }

    @Override
    public List<ProductSubCategory> getSubCategoryList(String lang) {
        return subCategoryRepo.findAllByLang(lang);
    }

    @Override
    public ProductSubCategory findSubCategoryById(int id) {
        return subCategoryRepo.findById(new Double(id).longValue()).get();
    }
}
