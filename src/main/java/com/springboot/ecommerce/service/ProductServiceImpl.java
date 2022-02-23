package com.springboot.ecommerce.service;

import com.springboot.ecommerce.model.Media;
import java.util.List;
import java.util.stream.Collectors;

import com.springboot.ecommerce.model.Product;
import com.springboot.ecommerce.model.ProductOptions;
import com.springboot.ecommerce.repository.ProductRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import org.json.JSONArray;
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
    
    @Autowired
    CloudinaryService cloudinaryService;


    @Override
    public Product createProduct(@RequestParam List<ProductOptions> poption, @RequestParam List<String> category,@RequestParam List<String> subcategory,@RequestParam MultiValueMap<String, Object> data,@RequestParam MultipartFile coverimage,@RequestParam List<MultipartFile> media) {
        
        Product product = new Product();
        
        /*
        i would get condition elements first    
        */
        if(data.containsKey("slider") && data.get("slider")!=null){
            String slider = data.get("slider").toString();
            product.setSlider(slider);
        }
        if(data.containsKey("unitareaprice") && data.get("unitareaprice")!=null){
            Double unitareaprice = new Double(data.get("unitareaprice").toString());
            product.setUnitareaprice(unitareaprice);
        }
        if(data.containsKey("customprices") && data.get("customprices")!=null){
            String customprices = data.get("customprices").toString();
            if(customprices.equalsIgnoreCase("1")){
                /*
                i would now consider poption
                */
            }
        }
        
        if(data.containsKey("category_id") && data.get("category_id")!=null){
            String categories = data.get("subcategories").toString();
            product.setCategory_id(categories);
        }
        
        if(data.containsKey("subcategory_id") && data.get("subcategory_id")!=null){
            String subcategories = data.get("subcategory").toString();
            product.setSubcategory_id(subcategories);
        }
        
        /*
        let's upload the cover image
        */
        try{
            Map upload = cloudinaryService.upload(coverimage);
            Media coverimg = new Media();
            coverimg.setUrl(upload.get("url").toString());
            coverimg.setType(upload.get("resource_type").toString());
            coverimg.setName(upload.get("original_filename").toString());
            coverimg.setExtension(upload.get("format").toString());
            coverimg.setSize(upload.get("bytes").toString());
            product.setCoverimage(coverimg);
            
        }
        catch(Exception e){
            System.out.println(" *******"+e.getMessage()+"********");
            
        }
        
        /*
        i would upload list of images if exist
        */
        try{
            List<Media> images = new ArrayList();
            for(MultipartFile image : media){
                try{
                    Map upload = cloudinaryService.upload(coverimage);
                    Media img = new Media();
                    img.setUrl(upload.get("url").toString());
                    img.setType(upload.get("resource_type").toString());
                    img.setName(upload.get("original_filename").toString());
                    img.setExtension(upload.get("format").toString());
                    img.setSize(upload.get("bytes").toString());
                    images.add(img);
                }
                catch(Exception e){
                    System.out.println(" *******"+e.getMessage()+"********");
                }
                
            }
            
            HashSet<Media> imgset = new HashSet(images);
            product.setMedia(imgset);
           
            
        }
        catch(Exception e){
            System.out.println(" *******"+e.getMessage()+"********");
            
        }
            
        Double price = new Double(data.get("price").toString());
        String name = data.get("name").toString();
        String shortdescription = data.get("shortdescription").toString();
        String longdescription = data.get("longdescription").toString();
        int quantiry = Integer.parseInt(data.get("quantiry").toString());
        String status = data.get("status").toString();
        String cuser = data.get("cuser").toString();
        String lang = data.get("lang").toString();
        
        product.setLang(lang);
        product.setPrice(price);
        product.setName(name);
        product.setLongdescription(longdescription);
        product.setShortdescription(shortdescription);
        product.setQuantiry(quantiry);
        product.setStatus(status);
        product.setCuser(cuser);
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
    public List<Product> getAllProduct(String lang, String dele){
        return productRepository.findAllProducts(lang,dele);
    }
    
    
}
