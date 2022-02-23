package com.springboot.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.springboot.ecommerce.util.StringListConverter;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="lang")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Languages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "code",unique=true)
    private String code;

    @Column(name = "flag")
    private String flag;
    
    @Column(name = "status")
    private String status;//1 = enable, 0 = disable

    public Languages() {
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    

}
