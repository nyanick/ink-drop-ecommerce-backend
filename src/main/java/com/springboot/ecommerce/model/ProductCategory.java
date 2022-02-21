package com.springboot.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.ecommerce.util.StringListConverter;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "ProductCategory")
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pc_id;
    @Column
    private String name_fr;
    
    @Column
    private String name_en;
    
    @Convert(converter = StringListConverter.class)
    private Set<String> subcategories = new HashSet<>();


    public int getPc_id() {
        return pc_id;//3
    }

    public void setPc_id(int pc_id) {
        this.pc_id = pc_id;
    }

    public String getName_fr() {
        return name_fr;
    }

    public void setName_fr(String name_fr) {
        this.name_fr = name_fr;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public Set<String> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(Set<String> subcategories) {
        this.subcategories = subcategories;
    }

    
    
    public Set<String> getSubCategories() {
        return subcategories;
    }

    public void setSubCategories(Set<String> subcategories) {
        this.subcategories = subcategories;
    }

}
