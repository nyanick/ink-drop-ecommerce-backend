package com.springboot.ecommerce.service;

import com.springboot.ecommerce.model.Languages;
import java.util.List;
import java.util.stream.Collectors;

import com.springboot.ecommerce.model.ProductCategory;
import com.springboot.ecommerce.repository.LanguagesRepository;
import com.springboot.ecommerce.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    LanguagesRepository languagesRepository;

    @Override
    public void createLanguage(Languages language) {
        languagesRepository.save(language);
    }

    @Override
    public Languages editLanguage(Languages language) {
        return languagesRepository.save(language);
    }

    @Override
    public Languages findLanguageById(int id) {
        return languagesRepository.findById(id).get();
    }

    @Override
    public void removeLanguageById(int id) {
        languagesRepository.deleteById(id);
    }

    @Override
    public List<Languages> getLanguageList() {
        return languagesRepository.findAll();
    }
    
    
   
    
}
