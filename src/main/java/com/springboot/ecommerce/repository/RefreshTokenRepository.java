/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.ecommerce.repository;

/**
 *
 * @author HP
 */
import com.springboot.ecommerce.model.RefreshToken;
import com.springboot.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    @Override
    Optional<RefreshToken> findById(Long id);
    Optional<RefreshToken> findByToken(String token);

    int deleteByUser(User get);
}