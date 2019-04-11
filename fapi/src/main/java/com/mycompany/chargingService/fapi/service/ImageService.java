package com.mycompany.chargingService.fapi.service;

import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    MultiValueMap<String, Object> uploadImage(MultipartFile image);


}
