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

import static com.accenture.product.enums.UrlEnum.PRODUCTS_URL;
import static com.accenture.product.enums.UrlEnum.PRODUCT_FORMAT;

@Service
public class InventoryRemoteService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;

    public List<ProductDTO> invokeGetProductService(String productCode) {
        String productUrl = String.format(PRODUCT_FORMAT.value(), PRODUCTS_URL.value(), productCode);
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
        LOG.info("invokeGetProductService : {}", PRODUCTS_URL.value());
        ResponseEntity<List<ProductDTO>> response = restTemplate.exchange(
                PRODUCTS_URL.value(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }
}
