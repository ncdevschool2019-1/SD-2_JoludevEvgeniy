package com.mycompany.chargingService.backend.controller;

import com.mycompany.chargingService.backend.entity.Subscript;
import com.mycompany.chargingService.backend.service.SubscriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public Iterable<Subscript> getAllSubscripts() {
        return this.subscriptService.getAllSubscripts();
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
    public Subscript saveSubscript(@RequestBody Subscript subscript) {
        return this.subscriptService.saveSubscript(subscript);
    }

}
