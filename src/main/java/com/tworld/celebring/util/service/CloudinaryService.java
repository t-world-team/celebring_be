package com.tworld.celebring.util.service;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    private final Cloudinary cloudinary;

    @Value("${cloudinary.preset-name}")
    private String CLOUDINARY_PRESET_NAME;

    @Value("${cloudinary.folder}")
    private String CLOUDINARY_FOLDER;


    public List<Map> unsignedUpload(List<MultipartFile> files) throws IOException {
        Map option = new HashMap();
        option.put("folder", CLOUDINARY_FOLDER);

        List<Map> result = new ArrayList<>();

        for (MultipartFile file: files) {
            Map uploadResult = cloudinary.uploader().unsignedUpload(file.getBytes(), CLOUDINARY_PRESET_NAME, option);
            result.add(uploadResult);
        }
        return result;
    }
}
