package com.mycompany.chargingService.fapi.service;

import com.mycompany.chargingService.fapi.models.Subscript;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SubscriptService {

    List<Subscript> getAllSubscripts();

    Subscript getSubscriptById(Long id);

    Subscript saveSubscript(Subscript subscript);

    void deleteSubscript(Long id);

    Subscript uploadImage(MultipartFile image, Long id);

    Resource getImage(String imageName);

}
