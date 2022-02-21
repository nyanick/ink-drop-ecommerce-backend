package com.springboot.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import com.springboot.ecommerce.model.Product;
import com.springboot.ecommerce.model.ProductOptions;
import com.springboot.ecommerce.repository.ProductRepository;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;


    @Override
    public Product createProduct(@RequestParam List<ProductOptions> poption, @RequestParam List<String> category,@RequestParam List<String> subcategory,@RequestParam MultiValueMap<String, Object> data,@RequestParam MultipartFile coverimage,@RequestParam List<MultipartFile> media) {
        
        Product product = new Product();
        
        /*
        i would get condition elements first    
        */
        if(data.containsKey("slider") && data.get("slider")!=null){
            String slider = data.get("slider").toString();
        }
        if(data.containsKey("unitareaprice") && data.get("unitareaprice")!=null){
            Double unitareaprice = new Double(data.get("unitareaprice").toString());
        }
        if(data.containsKey("customprices") && data.get("customprices")!=null){
            String customprices = data.get("customprices").toString();
            if(customprices.equalsIgnoreCase("1")){
                /*
                i would now consider poption
                */
            }
        }
            
        Double price = new Double(data.get("price").toString());
        String name = data.get("name").toString();
        String shortdescription = data.get("shortdescription").toString();
        String longdescription = data.get("longdescription").toString();
        int quantiry = Integer.parseInt(data.get("quantiry").toString());
        String status = data.get("status").toString();
        String cuser = data.get("cuser").toString();
        
        product.setCreatedDate(new Date());
        product.setModifiedDate(new Date());
        product.setDele("0");
        
        return productRepository.save(product);
    }


    @Override
    public Product editProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id,String dele) {
        return productRepository.findProductById(id, dele);
    }

    @Override
    public List<Product> getProductListInTheSameSubCategory(Long psc_id,String dele) {
        return productRepository.getProductsInTheSameSubCategory(psc_id, dele);
    }

    @Override
    public List<Product> getProductListInTheSameSubCategory(Long psc_id, Integer minCost, Integer maxCost, String dele) {
        return productRepository.getProductsInTheSameSubCategory(psc_id, minCost, maxCost, dele);
    }

    @Override
    public List<Product> getAllProductsUnderTheSameCategory(Long pc_id, String dele) {
        return productRepository.getProductsInTheSameCategory(pc_id, dele);
    }
    
    @Override
    public void saveProduct(Product product){
        productRepository.save(product);
        
    }
    
    @Override
    public List<Product> getAllProduct(String dele){
        return productRepository.findAllProducts(dele);
    }
    
    
}
