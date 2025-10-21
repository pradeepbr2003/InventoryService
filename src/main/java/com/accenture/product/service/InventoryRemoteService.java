package com.accenture.product.service;

import com.accenture.product.dto.ProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class InventoryRemoteService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;

    public List<ProductDTO> invokeGetProductService(String productUrl) {
        LOG.info("invokeGetProductService : {}", productUrl);
        ResponseEntity<List<ProductDTO>> response = restTemplate.exchange(
                productUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

    public String invokeRemoveProductService(String productUrl) {
        LOG.info("invokeRemoveProductService : {}", productUrl);
        ResponseEntity<String> response = restTemplate.exchange(
                productUrl,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

}
