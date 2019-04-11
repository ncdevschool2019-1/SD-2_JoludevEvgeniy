package com.mycompany.chargingService.backend.service;

import com.mycompany.chargingService.backend.entity.Subscript;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface SubscriptService {
    Subscript saveSubscript(Subscript subscript);
    Subscript getSubscriptById(Long id);
    Iterable<Subscript> getAllSubscripts();
    void deleteSubscript(Long id);
    Subscript uploadSubscriptsImage(MultipartFile image, Long id) throws IOException;
    Resource getImage(String imageName);
    void deleteImage(String imageName);

}
