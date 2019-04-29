package com.mycompany.chargingService.fapi.validators.impl;

import com.mycompany.chargingService.fapi.models.Subscript;
import com.mycompany.chargingService.fapi.validators.SubscriptValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptValidatorImpl implements SubscriptValidator {
    @Override
    public String subscriptsValidation(Subscript subscript, List<Subscript> subscripts) {
        if(subscript.getName().length() < 3 || subscript.getName().length() > 30){
            return "Lengths of your subscript name should be between 3 and 30 symbols";
        }
        if(subscript.getDescription().length() < 5 || subscript.getDescription().length() > 255){
            return "Lengths of your subscript description should be between 5 and 255 symbols";
        }
        if(subscript.getPrice() <= 0){
            return "Price of your subscript should be more then 0";
        }
        if(subscript.getPeriod() <= 0){
            return "Period of your subscript should be more then 0";
        }
        for(Subscript value : subscripts){
            if(value.getName().equals(subscript.getName()) && !value.getId().equals(subscript.getId())){
                return "Subscript with the same name already exists";
            }
        }
        return "Ok";
    }
}
