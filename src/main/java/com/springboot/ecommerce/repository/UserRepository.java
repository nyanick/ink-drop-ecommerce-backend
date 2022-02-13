package com.springboot.ecommerce.repository;
import com.springboot.ecommerce.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    
    
    
    @Query(value = "SELECT * FROM users e WHERE e.username = ?1 and e.password = ?2 and e.enabled = ?3",nativeQuery=true)
    Optional<User> findByUsernameAndPassword(String username, String password, Boolean enabled);
    

}