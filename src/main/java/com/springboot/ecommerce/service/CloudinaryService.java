/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.ecommerce.service;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;


import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import org.slf4j.LoggerFactory;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


/**
 *
 * @author HP
 */
@Service
public class CloudinaryService {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Cloudinary cloudinary = Singleton.getCloudinary();

    /**
     * Upload a MultipartFile to Cloudinary. More info:
     * https://stackoverflow.com/a/39572293
     *
     * @param file to be uploaded
     * @return the publicId assigned to the uploaded file, or null in case of
     * error
     */
    public Map upload(MultipartFile file) {
        logger.trace("Called CloudinaryService.upload with args: the multipart file");
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String publicId = uploadResult.get("public_id").toString();
            logger.info("successfully uploaded the file: " + publicId);
            return uploadResult;
        } catch (Exception ex) {
            logger.error("failed to load to Cloudinary the image file: " + file.getName());
            logger.error(ex.getMessage());
            return null;
        }
    }
    

 

    /**
     * Download an image from Cloudinary
     *
     * @param publicId of the image
     * @param width
     * @param isAvatar
     * @param height
     * @return
     */
    public ResponseEntity<ByteArrayResource> downloadImg(String publicId, int width, int height, boolean isAvatar) {

        logger.info("Requested to download the image file: " + publicId);

        // Generates the URL
        String format = "jpg";
        Transformation transformation = new Transformation().width(width).height(height).crop("fill");
        if (isAvatar) {
            // transformation = transformation.gravity("face").radius("max");
            transformation = transformation.radius("max");
            format = "png";
        }
        String cloudUrl = cloudinary.url().secure(true).format(format)
                .transformation(transformation)
                .publicId(publicId)
                .generate();

        logger.debug("Generated URL of the image to be downloaded: " + cloudUrl);

        try {
            // Get a ByteArrayResource from the URL
            URL url = new URL(cloudUrl);
            InputStream inputStream = url.openStream();
            byte[] out = org.apache.commons.io.IOUtils.toByteArray(inputStream);
            ByteArrayResource resource = new ByteArrayResource(out);

            // Creates the headers
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("content-disposition", "attachment; filename=image.jpg");
            responseHeaders.add("Content-Type", "image/jpeg");

            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .contentLength(out.length)
                    // .contentType(MediaType.parseMediaType(mimeType))
                    .body(resource);

        } catch (Exception ex) {
            logger.error("FAILED to download the file: " + publicId);
            return null;
        }
    }
}
