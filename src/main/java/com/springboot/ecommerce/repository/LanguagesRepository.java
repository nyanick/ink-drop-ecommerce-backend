package com.springboot.ecommerce.repository;
import com.springboot.ecommerce.model.Languages;
import com.springboot.ecommerce.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface LanguagesRepository extends JpaRepository<Languages, Integer> {
    
    Languages findByCode(String code);


}