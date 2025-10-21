package com.accenture.product;


import com.accenture.product.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryServiceApplication {

    @Autowired
    private InventoryService invService;

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

}