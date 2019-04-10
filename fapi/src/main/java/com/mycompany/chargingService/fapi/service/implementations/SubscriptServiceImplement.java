package com.mycompany.chargingService.fapi.service.implementations;

import com.mycompany.chargingService.fapi.models.Subscript;
import com.mycompany.chargingService.fapi.service.SubscriptService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class SubscriptServiceImplement implements SubscriptService {

    @Value("${backend.server.url}api/subscripts")
    private String backendServerUrl;

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<Subscript> getAllSubscripts() {
        Subscript[] subscripts = restTemplate.getForEntity(backendServerUrl, Subscript[].class).getBody();
        return subscripts == null ? Collections.emptyList() : Arrays.asList(subscripts);
    }

    @Override
    public Subscript getSubscriptById(Long id) {
        return restTemplate.getForEntity(backendServerUrl + "/" + id, Subscript.class).getBody();
    }

    @Override
    public Subscript saveSubscript(Subscript subscript) {
        return restTemplate.postForEntity(backendServerUrl, subscript, Subscript.class).getBody();
    }

    @Override
    public void deleteSubscript(Long id) {
        restTemplate.delete(backendServerUrl + "/" + id);
    }
}
