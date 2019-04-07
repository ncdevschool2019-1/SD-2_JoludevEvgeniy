package com.mycompany.chargingService.backend.controller;

import com.mycompany.chargingService.backend.entity.ActiveSubscript;
import com.mycompany.chargingService.backend.service.ActiveSubscriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/active-subscripts")
public class ActiveSubscriptController {

    private ActiveSubscriptService activeSubscriptService;

    @Autowired
    public ActiveSubscriptController(ActiveSubscriptService activeSubscriptService) {
        this.activeSubscriptService = activeSubscriptService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ActiveSubscript> saveActiveSubscript(@RequestBody ActiveSubscript activeSubscript) {
        Long id = this.activeSubscriptService.saveActiveSubscript(activeSubscript).getId();
        Optional<ActiveSubscript> savedActiveSubscript = this.activeSubscriptService.getActiveSubscriptById(id);
        if (savedActiveSubscript.isPresent()) {
            return ResponseEntity.ok(savedActiveSubscript.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteActiveSubscript(@PathVariable(name = "id") Long id) {
        this.activeSubscriptService.deleteActiveSubscript(id);

    }

}
