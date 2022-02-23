package com.springboot.ecommerce.controller;

import com.springboot.ecommerce.model.Languages;
import com.springboot.ecommerce.service.LanguageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/settings")
@Tag(name = "Settings API")
public class SettingsController {

    @Autowired
    LanguageService languagesService;

    
    @PostMapping(value = "/langauge", headers = "Accept=application/json")
    public ResponseEntity<Void> createNewLanguage(@RequestBody Languages lang, UriComponentsBuilder ucBuilder) {
        languagesService.createLanguage(lang);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/langauge/{id}").buildAndExpand(lang.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
    
    @PutMapping(value="/langauge/", headers="Accept=application/json")
    public Languages updateLanguage(@RequestBody Languages lang)
    {
        return  languagesService.editLanguage(lang);
    }

    @DeleteMapping(value="/langauge/{id}", headers ="Accept=application/json")
    public ResponseEntity<Languages> deleteLanguage(@Parameter(description = "ID of the langauge") @PathVariable("id") int id){
        Languages lang = languagesService.findLanguageById(id);
        if (lang == null) {
            return new ResponseEntity<Languages>(HttpStatus.NOT_FOUND);
        }
        languagesService.removeLanguageById(id);
        return new ResponseEntity<Languages>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/langauge/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Languages> getLanguageById(@Parameter(description = "ID of the langauge") @PathVariable("id") int id) {

        Languages lang = languagesService.findLanguageById(id);

        if (lang == null) {
            return new ResponseEntity<Languages>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Languages>(lang, HttpStatus.OK);
    }

    @GetMapping(value="/languages", headers="Accept=application/json")
    public List<Languages> getAllLanguages() {
        return languagesService.getLanguageList();
    }
}
