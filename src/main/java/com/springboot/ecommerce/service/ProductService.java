package com.springboot.ecommerce.service;

import com.springboot.ecommerce.model.Product;
import com.springboot.ecommerce.model.ProductOptions;

import java.util.List;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    public Product createProduct(@RequestParam List<ProductOptions> poption, @RequestParam List<String> category,@RequestParam List<String> subcategory,@RequestParam MultiValueMap<String, Object> data,@RequestParam MultipartFile coverimage,@RequestParam List<MultipartFile> media);
    public Product editProduct(Product product);
    public Product findProductById(Long id, String dele);
    public List<Product> getProductListInTheSameSubCategory(Long psc_id, String dele);
    public List<Product> getProductListInTheSameSubCategory(Long psc_id, Integer minCost, Integer maxCost,String dele);
    public List<Product> getAllProductsUnderTheSameCategory(Long pc_id, String dele);
    public void saveProduct(Product product);
    public List<Product> getAllProduct(String dele);
}
