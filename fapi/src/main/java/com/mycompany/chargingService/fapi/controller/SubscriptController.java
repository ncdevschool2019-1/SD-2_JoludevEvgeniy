package com.mycompany.chargingService.fapi.controller;

import com.mycompany.chargingService.fapi.models.Subscript;
import com.mycompany.chargingService.fapi.service.SubscriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api/subscripts")
public class SubscriptController {

    private SubscriptService subscriptService;

    @Autowired
    public SubscriptController(SubscriptService subscriptService) {
        this.subscriptService = subscriptService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Subscript>> getAllSubscripts() {
        return ResponseEntity.ok(this.subscriptService.getAllSubscripts());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Subscript> getSubscriptById(@PathVariable(name = "id") Long id) {
        Subscript subscript = this.subscriptService.getSubscriptById(id);
        if (subscript != null) {
            return ResponseEntity.ok(subscript);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Subscript> saveSubscript(@RequestBody Subscript subscript) {
        Subscript savedSubscript = this.subscriptService.saveSubscript(subscript);
        if (savedSubscript != null) {
            return ResponseEntity.ok(savedSubscript);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteSubscript(@PathVariable(name = "id") Long id) {
        this.subscriptService.deleteSubscript(id);
    }

    @RequestMapping(value = "/image/{id}", method = RequestMethod.POST)
    public ResponseEntity<Subscript> uploadImage(MultipartHttpServletRequest request, @PathVariable(name = "id") Long id) {
        Iterator<String> itr = request.getFileNames();
        Subscript user = this.subscriptService.uploadImage(request.getFile(itr.next()), id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/image/{imageName:.+}", method = RequestMethod.GET)
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {

        try {
            Resource image = this.subscriptService.getImage(imageName);
            if (image.exists() || image.isReadable()) {
                return ResponseEntity.ok(image);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }
}

