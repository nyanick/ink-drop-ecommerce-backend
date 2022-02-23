package com.springboot.ecommerce.repository;

import com.springboot.ecommerce.model.Product;
import com.springboot.ecommerce.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    @Query(value = "SELECT * FROM product e WHERE e.dele = ?1",nativeQuery=true)
    public List<Product> getProductsInTheSameSubCategory(Long psc_id, String dele);

    @Query(value = "SELECT * FROM product e WHERE e.dele = ?1",nativeQuery=true)
    public List<Product> getProductsInTheSameCategory(Long pc_id, String dele);

    @Query(value = "SELECT * FROM product e WHERE e.dele = ?1",nativeQuery=true)
    public List<Product> getProductsInTheSameSubCategory(Long psc_id, Integer minCost, Integer maxCost, String dele);

    @Query(value = "SELECT * FROM product e WHERE e.dele = ?2",nativeQuery=true)
    public List<Product> findAllProducts(String lang, String dele);
    
    @Query(value = "SELECT * FROM product e WHERE e.id=?1 and e.dele = ?2",nativeQuery=true)
    public Product findProductById(Long id, String dele);

    
}
