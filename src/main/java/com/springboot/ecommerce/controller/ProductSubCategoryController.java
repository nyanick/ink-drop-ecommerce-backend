package com.springboot.ecommerce.controller;

import com.springboot.ecommerce.model.ProductSubCategory;
import com.springboot.ecommerce.service.ProductSubCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/product-subcategory")
@Tag(name = "Product SubCategory API")
public class ProductSubCategoryController {

    @Autowired
    ProductSubCategoryService subCategoryService;

    
    @PostMapping(value = "/subcategory", headers = "Accept=application/json")
    public ResponseEntity<Void> createNewSubCategory(@RequestBody ProductSubCategory subCategory, UriComponentsBuilder ucBuilder) {
        subCategoryService.createSubCategory(subCategory);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/subcategory/{id}").buildAndExpand(subCategory.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
    
    @PutMapping(value="/subcategory/", headers="Accept=application/json")
    public ProductSubCategory updateSubCategory(@RequestBody ProductSubCategory subCategory)
    {
        return  subCategoryService.editSubCategory(subCategory);
    }

    @DeleteMapping(value="/subcategory/{id}", headers ="Accept=application/json")
    public ResponseEntity<ProductSubCategory> deleteSubCategory(@Parameter(description = "ID of the product subcategory") @PathVariable("id") int id){
        ProductSubCategory subCategory = subCategoryService.findSubCategoryById(id);
        if (subCategory == null) {
            return new ResponseEntity<ProductSubCategory>(HttpStatus.NOT_FOUND);
        }
        subCategoryService.removeSubCategoryById(id);
        return new ResponseEntity<ProductSubCategory>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/subcategory/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductSubCategory> getSubCategoryById(@Parameter(description = "ID of the product subcategory") @PathVariable("id") int id) {

        ProductSubCategory subCategory = subCategoryService.findSubCategoryById(id);

        if (subCategory == null) {
            return new ResponseEntity<ProductSubCategory>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ProductSubCategory>(subCategory, HttpStatus.OK);
    }

    @GetMapping(value="/subcategories/{lang}", headers="Accept=application/json")
    public List<ProductSubCategory> getAllSubCategories(@PathVariable String lang) {
        return subCategoryService.getSubCategoryList(lang);
    }
}
