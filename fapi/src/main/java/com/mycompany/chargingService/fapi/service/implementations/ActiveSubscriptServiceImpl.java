package com.mycompany.chargingService.fapi.service.implementations;

import com.mycompany.chargingService.fapi.models.ActiveSubscript;
import com.mycompany.chargingService.fapi.service.ActiveSubscriptService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ActiveSubscriptServiceImpl implements ActiveSubscriptService {

    @Value("${backend.server.url}api/active-subscripts")
    private String backendServerUrl;

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<ActiveSubscript> getAllActiveSubscripts() {
        ActiveSubscript[] activeSubscripts = restTemplate.getForEntity(backendServerUrl, ActiveSubscript[].class).getBody();
        return activeSubscripts == null ? Collections.emptyList() : Arrays.asList(activeSubscripts);
    }

    @Override
    public ActiveSubscript getActiveSubscriptById(Long id) {
        return restTemplate.getForEntity(backendServerUrl + "/" + id, ActiveSubscript.class).getBody();
    }

    @Override
    public ActiveSubscript saveActiveSubscript(ActiveSubscript activeSubscript) {
        return restTemplate.postForEntity(backendServerUrl, activeSubscript, ActiveSubscript.class).getBody();
    }

    @Override
    public void deleteActiveSubscript(Long id) {
        restTemplate.delete(backendServerUrl + "/" + id);
    }
}
