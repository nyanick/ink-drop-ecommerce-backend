package com.springboot.ecommerce.service;

import com.springboot.ecommerce.model.Languages;
import com.springboot.ecommerce.model.ProductCategory;

import java.util.List;

public interface LanguageService {

    public void createLanguage(Languages language);

    public Languages editLanguage(Languages language);

    public Languages findLanguageById(int id);

    public void removeLanguageById(int id);

    public List<Languages> getLanguageList();
    
}
