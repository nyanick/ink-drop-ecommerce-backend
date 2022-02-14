package com.springboot.ecommerce.service;

import com.springboot.ecommerce.model.User;
import com.springboot.ecommerce.repository.UserRepository;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.annotation.Transactional;


@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;



//        @Override
//        @Transactional
//        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//          User user = userRepository.findByUsername(username)
//              .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
//
//          return UserDetailsImpl.build(user);
//        }
        
        @Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(name);
		if (user == null) {
			throw new UsernameNotFoundException("Client not found with username: " + name);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}
        
        
        public UserDetails loadUserByUsernameAndPassword(String username, String password) throws BadCredentialsException {
          User user = userRepository.findByUsernameAndPassword(username, password, Boolean.TRUE);
          if(user==null){
              new BadCredentialsException("Incorrect credentials or user deactivated: " + username);
          }
          return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
          //return UserDetailsImpl.build(user);
        }

	
}