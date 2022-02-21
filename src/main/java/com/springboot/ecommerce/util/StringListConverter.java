/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.ecommerce.util;

/**
 *
 * @author HP
 */
import java.util.Arrays;
import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static java.util.Collections.*;
import java.util.HashSet;

@Converter
public class StringListConverter implements AttributeConverter<HashSet<String>, String> {
    private static final String SPLIT_CHAR = ";";
    
    @Override
    public String convertToDatabaseColumn(HashSet<String> stringList) {
        return stringList != null ? String.join(SPLIT_CHAR, stringList) : "";
    }

    @Override
    public HashSet<String> convertToEntityAttribute(String string) {
        
        try{
            return new HashSet<>(Arrays.asList(string.split(SPLIT_CHAR)));
        }
        catch(Exception e){
            //System.out.println(" An Error occured: "+e.getMessage());
            return null;
        }
    }
}