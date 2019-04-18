package com.mycompany.chargingService.fapi.service.implementations;

import com.mycompany.chargingService.fapi.service.ImageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageServiceImpl implements ImageService {

    @Override
    public MultiValueMap<String, Object> uploadImage(MultipartFile image) {
        if (image.isEmpty()) {
            return null;
        }
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        try {
            body.add("image", new ByteArrayResource(image.getBytes()) {
                @Override
                public String getFilename() {
                    return image.getOriginalFilename();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;
    }
}
