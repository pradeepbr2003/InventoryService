package com.accenture.product.service;

import com.accenture.product.config.InventoryPropUrlConfig;
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

    @Autowired
    private InventoryPropUrlConfig invPropUrlConfig;

    public List<ProductDTO> invokeGetProductService(String productCode) {
        String productUrl = invPropUrlConfig.getProductByCodeUrl(productCode);
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

    public List<ProductDTO> invokeGetProductService() {
        LOG.info("invokeGetProductService : {}", invPropUrlConfig.getUrl());
        ResponseEntity<List<ProductDTO>> response = restTemplate.exchange(
                invPropUrlConfig.getUrl(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }
}
