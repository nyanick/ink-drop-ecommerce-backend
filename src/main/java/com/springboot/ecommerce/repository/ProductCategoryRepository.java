package com.springboot.ecommerce.repository;

import com.springboot.ecommerce.model.ProductCategory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    
}
