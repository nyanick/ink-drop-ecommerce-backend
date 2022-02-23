package com.springboot.ecommerce.repository;

import com.springboot.ecommerce.model.ProductCategory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Query(value="selct * from categories where lang =?1", nativeQuery=true)
    public List<ProductCategory> findAllByLang(String lang);

    
}
