package com.springboot.ecommerce.security;


import com.springboot.ecommerce.service.JwtUserDetailsService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@EnableWebSecurity
public class AuthenticationSettings extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private DataSource dataSource;
    
    
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private AuthTokenFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
        .userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .httpBasic().and()
//                .csrf().disable().authorizeRequests()
//                .antMatchers("/swagger-ui*", "/api-docs/","/swagger-ui/**").permitAll()
//                .antMatchers("/login", "/register").permitAll()
////                .anyRequest().permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic()
//                .and()
//                .csrf().disable().
//                exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//		// Add a filter to validate the tokens with every request
//		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);;
//    }
    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {



        httpSecurity.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());

        httpSecurity.cors().configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                return new CorsConfiguration().applyPermitDefaultValues();
            }
        });

            // We don't need CSRF for this example
            httpSecurity.csrf().disable()
                            // dont authenticate this particular request
                            .authorizeRequests().antMatchers("/authenticate","/auth/**","/signup","/swagger-ui*", "/api-docs", "/api-docs/*","/swagger-ui/*").permitAll().

                            // all other requests need to be authenticated
                            anyRequest().authenticated().and().
                            //anyRequest().permitAll().and().

                            // make sure we use stateless session; session won't be used to
                            // store user's state.
                                            exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            // Add a filter to validate the tokens with every request
            httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
    }
    
}
