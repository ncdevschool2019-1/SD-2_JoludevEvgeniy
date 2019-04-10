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

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<ActiveSubscript>> getAllActiveSubscripts() {
        return ResponseEntity.ok(this.activeSubscriptService.getAllActiveSubscripts());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ActiveSubscript> getActiveSubscriptById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(this.activeSubscriptService.getActiveSubscriptById(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ActiveSubscript> saveActiveSubscript(@RequestBody ActiveSubscript activeSubscript) {
        Long id = this.activeSubscriptService.saveActiveSubscript(activeSubscript).getId();
        return ResponseEntity.ok(this.activeSubscriptService.getActiveSubscriptById(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteActiveSubscript(@PathVariable(name = "id") Long id) {
        this.activeSubscriptService.deleteActiveSubscript(id);

    }

}
