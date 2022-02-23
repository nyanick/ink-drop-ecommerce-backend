package com.springboot.ecommerce.repository;

import com.springboot.ecommerce.model.ProductSubCategory;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface ProductSubCategoryRepository extends JpaRepository<ProductSubCategory, Long>{

    @Query(value="selct * from subcategories where lang =?1", nativeQuery=true)
    public List<ProductSubCategory> findAllByLang(String lang);
    
}
