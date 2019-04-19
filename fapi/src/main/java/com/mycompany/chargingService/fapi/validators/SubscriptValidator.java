package com.mycompany.chargingService.fapi.validators;

import com.mycompany.chargingService.fapi.models.Subscript;

import java.util.List;

public interface SubscriptValidator {

    String subscriptsValidation(Subscript subscript, List<Subscript> subscripts);
}
