package com.mycompany.chargingService.backend.controller;

import com.mycompany.chargingService.backend.entity.ActiveSubscript;
import com.mycompany.chargingService.backend.service.ActiveSubscriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/active-subscripts")
public class ActiveSubscriptController {

    private ActiveSubscriptService activeSubscriptService;

    @Autowired
    public ActiveSubscriptController(ActiveSubscriptService activeSubscriptService) {
        this.activeSubscriptService = activeSubscriptService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ActiveSubscript saveActiveSubscript(@RequestBody ActiveSubscript activeSubscript){
        return this.activeSubscriptService.saveActiveSubscript(activeSubscript);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteActiveSubscript(@PathVariable(name = "id") Long id){
        this.activeSubscriptService.deleteActiveSubscript(id);
    }

}
