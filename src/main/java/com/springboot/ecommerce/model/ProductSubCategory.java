package com.springboot.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ProductSubCategory")
public class ProductSubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int psc_id;

    @Column
    private String name_en;
    
    @Column
    private String name_fr;

    public int getPsc_id() {
        return psc_id;
    }

    public void setPsc_id(int psc_id) {
        this.psc_id = psc_id;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getName_fr() {
        return name_fr;
    }

    public void setName_fr(String name_fr) {
        this.name_fr = name_fr;
    }
    
    
}
