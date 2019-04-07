package com.mycompany.chargingService.backend.service;

import com.mycompany.chargingService.backend.entity.Subscript;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface SubscriptService {
    Subscript saveSubscript(Subscript subscript);
    Optional<Subscript> getSubscriptById(Long id);
    Iterable<Subscript> getAllSubscripts();
    void deleteSubscript(Long id);
    String uploadSubscriptsImage(MultipartFile image, Long id) throws IOException;

}
