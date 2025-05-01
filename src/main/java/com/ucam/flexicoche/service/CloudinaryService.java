package com.ucam.flexicoche.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryService {
    
    
    @Autowired
    private Cloudinary cloudinary;

    public String uploadImage(MultipartFile file, String carpetaDestino) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
            "folder", carpetaDestino
        ));

        return uploadResult.get("secure_url").toString(); 
    }    
}
