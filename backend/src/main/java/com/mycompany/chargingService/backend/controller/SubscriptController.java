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
        Iterable<Subscript> subscripts = this.subscriptService.getAllSubscripts();
        if (subscripts.iterator().hasNext()) {
            return ResponseEntity.ok(subscripts);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Subscript> getSubscriptById(@PathVariable(name = "id") Long id) {
        Optional<Subscript> subscript = this.subscriptService.getSubscriptById(id);
        if (subscript.isPresent()) {
            return ResponseEntity.ok(subscript.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteSubscript(@PathVariable(name = "id") Long id) {
        this.subscriptService.deleteSubscript(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Subscript> saveSubscript(@RequestBody Subscript subscript) {
        Long id = this.subscriptService.saveSubscript(subscript).getId();
        Optional<Subscript> savedSubscript = this.subscriptService.getSubscriptById(id);
        if (savedSubscript.isPresent()) {
            return ResponseEntity.ok(savedSubscript.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @RequestMapping(value = "/image/{id}", method = RequestMethod.POST)
    public void uploadFile(MultipartHttpServletRequest request, @PathVariable(name = "id") Long id) throws IOException {

        Iterator<String> itr = request.getFileNames();
        this.subscriptService.uploadSubscriptsImage(request.getFile(itr.next()), id);
    }
}
