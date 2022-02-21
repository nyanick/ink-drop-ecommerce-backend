/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.ecommerce.controller;

import com.springboot.ecommerce.service.CloudinaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping("/cloud")
public class CloudinaryController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CloudinaryService cloudinaryService;

    /**
     * Upload a MultipartFile to Cloudinary.
     *
     * @param file
     * @return the publicId assigned to the uploaded file, or null in case of
     * error
     */
    @PostMapping("/upload")
    public @ResponseBody
    String upload( @RequestParam("file") MultipartFile file) {
        return cloudinaryService.upload( file);
    }
    
   

    /**
     * Download an image from the cloud, scaled and cropped the given size
     *
     * @param publicId of the image, returned by the upload() method
     * @param width in px
     * @param height in px
     * @return
     */
    @GetMapping("/downloadImg/{publicId}/{width}/{height}")
    public @ResponseBody
    ResponseEntity<ByteArrayResource> downloadImg(@PathVariable String publicId, @PathVariable int width, @PathVariable int height) {
        return cloudinaryService.downloadImg(publicId, width, height, false);
    }
    
    /**
     * Download an rounded avatar from the cloud, scaled and cropped the given size
     *
     * @param publicId of the image, returned by the upload() method
     * @param size in px
     * @return
     */
    @GetMapping("/downloadAvatar/{publicId}/{size}")
    public @ResponseBody
    ResponseEntity<ByteArrayResource> downloadAvatar(@PathVariable String publicId, @PathVariable int size) {
        return cloudinaryService.downloadImg(publicId, size, size, true);
    }

}
