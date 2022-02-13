package com.springboot.ecommerce.controller;

import com.springboot.ecommerce.model.RefreshToken;
import com.springboot.ecommerce.payload.request.TokenRefreshRequest;
import com.springboot.ecommerce.payload.response.TokenRefreshResponse;
import com.springboot.ecommerce.security.JwtTokenUtil;
import com.springboot.ecommerce.service.JwtUserDetailsService;
import com.springboot.ecommerce.service.RefreshTokenService;
import com.springboot.ecommerce.exceptions.TokenRefreshException;
import com.springboot.ecommerce.model.ERole;
import com.springboot.ecommerce.model.Role;
import com.springboot.ecommerce.model.User;
import com.springboot.ecommerce.payload.request.LogOutRequest;
import com.springboot.ecommerce.payload.request.LoginRequest;
import com.springboot.ecommerce.payload.request.SignupRequest;
import com.springboot.ecommerce.payload.response.JwtResponse;
import com.springboot.ecommerce.payload.response.MessageResponse;
import com.springboot.ecommerce.repository.RoleRepository;
import com.springboot.ecommerce.repository.UserRepository;
import com.springboot.ecommerce.service.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")
@Tag(name = "Authentication API")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    JwtUserDetailsService userDetailsService;

    @Autowired
    RefreshTokenService refreshTokenService;
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

//        Authentication authentication = authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        
        final UserDetails userDetails1 = userDetailsService.loadUserByUsernameAndPassword(loginRequest.getUsername(),this.encoder.encode(loginRequest.getPassword()) );
        
        String jwt = jwtTokenUtil.generateToken(userDetails1);

        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        user.setAddress(signUpRequest.getAddress());
        user.setCountry(signUpRequest.getCountry());
        user.setTel(signUpRequest.getTel());
        user.setDob(signUpRequest.getDob());
        user.setCreatedDate(new Date());
        user.setFirstname(signUpRequest.getFirstname());
        user.setLastname(signUpRequest.getLastname());
        user.setEnabled(true);
        user.setModifiedDate(new Date());
        
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    
    @PostMapping("/refreshtoken")
    public Object refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    final UserDetails userDetails1 = userDetailsService.loadUserByUsername(user.getUsername());
                    String token = jwtTokenUtil.generateToken(userDetails1);
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,"Refresh token is not in database!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) {
        refreshTokenService.deleteByUserId(logOutRequest.getUserId());
        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
    }
    

}
