package com.mycompany.chargingService.fapi.controller;

import com.mycompany.chargingService.fapi.models.Subscript;
import com.mycompany.chargingService.fapi.service.SubscriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
