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
@Table(name="product")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "sdesp")
    private String shortdescription;

    @Column(name = "fdesp")
    private int longdescription;
    
    @Convert(converter = StringListConverter.class)
    private HashSet<String> category = new HashSet();//would store the Id of sub categories
    
    @Convert(converter = StringListConverter.class)
    private HashSet<String> subcategory = new HashSet();//would store the Id of sub categories

    @Column(name="qty")
    private int quantiry;
    
    @Column(name="price")
    private Double price;
    
    @ManyToOne
    @JoinColumn(name = "image_id")
    @JsonIgnoreProperties({ "avatar" })
    private Media coverimage;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JsonManagedReference
    @JoinTable(name = "product_media", joinColumns = @JoinColumn(name = "p_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "m_id", referencedColumnName = "id"))
    private Set<Media> media;
    
    private String status; //1 active 2 not active
    
    private String slider;// 1 = yes, 0 is no
    
    private String customprices;//1 = yes, we would used the customer price object , 0= no, use just the fixed price

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JsonManagedReference
    @JoinTable(name = "product_options", joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "option_id", referencedColumnName = "id"))
    private Set<ProductOptions> itemset;
    
    /*
    But if itemset is empty and customprices is 1, the would be use the unit area price
    */
    @Column(name="areaprice")
    private Double unitareaprice;
    
    
    @Column(name = "doc", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Africa/Douala")
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    
    @Column(name = "dom", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Africa/Douala")
    @Temporal(TemporalType.DATE)
    private Date modifiedDate;
    
    private String cuser;
    
    private String muser;
    
    private String f1;
    
    private String f2;
    
    private String f3;
    
    private String f4;
    
    private String f5;
    
    private String dele;// 0= not deleted, 1 = deleted

    public Product() {
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

    public String getShortdescription() {
        return shortdescription;
    }

    public void setShortdescription(String shortdescription) {
        this.shortdescription = shortdescription;
    }

    public int getLongdescription() {
        return longdescription;
    }

    public void setLongdescription(int longdescription) {
        this.longdescription = longdescription;
    }

    public HashSet<String> getCategory() {
        return category;
    }

    public void setCategory(HashSet<String> category) {
        this.category = category;
    }

    public HashSet<String> getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(HashSet<String> subcategory) {
        this.subcategory = subcategory;
    }

    

    public int getQuantiry() {
        return quantiry;
    }

    public void setQuantiry(int quantiry) {
        this.quantiry = quantiry;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Media getCoverimage() {
        return coverimage;
    }

    public void setCoverimage(Media coverimage) {
        this.coverimage = coverimage;
    }

    public Set<Media> getMedia() {
        return media;
    }

    public void setMedia(Set<Media> media) {
        this.media = media;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSlider() {
        return slider;
    }

    public void setSlider(String slider) {
        this.slider = slider;
    }

    public String getCustomprices() {
        return customprices;
    }

    public void setCustomprices(String customprices) {
        this.customprices = customprices;
    }

    public Set<ProductOptions> getItemset() {
        return itemset;
    }

    public void setItemset(Set<ProductOptions> itemset) {
        this.itemset = itemset;
    }

    public Double getUnitareaprice() {
        return unitareaprice;
    }

    public void setUnitareaprice(Double unitareaprice) {
        this.unitareaprice = unitareaprice;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCuser() {
        return cuser;
    }

    public void setCuser(String cuser) {
        this.cuser = cuser;
    }

    public String getMuser() {
        return muser;
    }

    public void setMuser(String muser) {
        this.muser = muser;
    }

    
    
    
    public String getF1() {
        return f1;
    }

    public void setF1(String f1) {
        this.f1 = f1;
    }

    public String getF2() {
        return f2;
    }

    public void setF2(String f2) {
        this.f2 = f2;
    }

    public String getF3() {
        return f3;
    }

    public void setF3(String f3) {
        this.f3 = f3;
    }

    public String getF4() {
        return f4;
    }

    public void setF4(String f4) {
        this.f4 = f4;
    }

    public String getF5() {
        return f5;
    }

    public void setF5(String f5) {
        this.f5 = f5;
    }

    public String getDele() {
        return dele;
    }

    public void setDele(String dele) {
        this.dele = dele;
    }
    
    
    

}
