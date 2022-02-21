package com.springboot.ecommerce;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.cloudinary.SingletonManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(exclude = { HibernateJpaAutoConfiguration.class})
//@EnableAutoConfiguration
public class EcommerceApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(EcommerceApplication.class);
    }
    public static void main(String[] args) {
        // Set Cloudinary instance
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "vim", // insert here you cloud name
                "api_key", "585424897696247", // insert here your api code
                "api_secret", "vpF1qPKa82hLVvZnj6oZIx6ILik")); // insert here your api secret
        SingletonManager manager = new SingletonManager();
        manager.setCloudinary(cloudinary);
        manager.init();
        SpringApplication.run(EcommerceApplication.class, args);
    }
}
