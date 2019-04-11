package com.mycompany.chargingService.backend.controller;

import com.mycompany.chargingService.backend.entity.Subscript;
import com.mycompany.chargingService.backend.service.SubscriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/subscripts")
public class SubscriptController {

    private SubscriptService subscriptService;

    @Autowired
    public SubscriptController(SubscriptService subscriptService) {
        this.subscriptService = subscriptService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Subscript>> getAllSubscripts() {
        return ResponseEntity.ok(this.subscriptService.getAllSubscripts());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Subscript> getSubscriptById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(this.subscriptService.getSubscriptById(id));

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteSubscript(@PathVariable(name = "id") Long id) {
        if (this.subscriptService.getSubscriptById(id) != null) {
            this.subscriptService.deleteImage(this.subscriptService.getSubscriptById(id).getImagePath());
            this.subscriptService.deleteSubscript(id);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Subscript> saveSubscript(@RequestBody Subscript subscript) {
        Long id = this.subscriptService.saveSubscript(subscript).getId();
        return ResponseEntity.ok(this.subscriptService.getSubscriptById(id));
    }

    @RequestMapping(value = "/image/{id}", method = RequestMethod.POST)
    public ResponseEntity<Subscript> uploadFile(MultipartHttpServletRequest request, @PathVariable(name = "id") Long id) throws IOException {

        Iterator<String> itr = request.getFileNames();
        return ResponseEntity.ok(this.subscriptService.uploadSubscriptsImage(request.getFile(itr.next()), id));

    }

    @RequestMapping(value = "/image/{imageName:.+}", method = RequestMethod.GET)
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
        Resource image = null;
        try {
            image = this.subscriptService.getImage(imageName);
            if (!image.isReadable()) {
                image = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(image);
    }
}
