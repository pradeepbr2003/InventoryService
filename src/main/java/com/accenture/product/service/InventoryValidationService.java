package com.accenture.product.service;

import com.accenture.product.dto.InventoryDTO;
import com.accenture.product.entity.Inventory;
import com.accenture.product.jpa.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryValidationService {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InventoryRepository invRepository;

    /**
     * Need to check whether already inventory exists for same product
     *
     * @param inventory
     */
    public void validateInventoryAvailable(InventoryDTO inventory) {
        LOG.info("Invoking validateInventoryAvailable method");
        Optional<Inventory> optInventory = invRepository.findByProductId(inventory.getProduct().getCode());
        LOG.info("optInventory : {} ", optInventory.isPresent());
        if (optInventory.isPresent()) {
            throw new RuntimeException(String.format("Inventory already available : %s", optInventory.get()));
        }
    }

    /**
     * Need to check whether already inventory exists for same product
     *
     * @param inventories
     */
    public void validateInventoryAvailable(List<InventoryDTO> inventories) {
        LOG.info("Invoking validateInventoryAvailable method");
        List<String> productCodes = inventories.stream().map(in -> in.getProduct().getCode()).toList();
        LOG.info("productCodes : {}", productCodes);
        List<Inventory> inventoryList = invRepository.findAllByProductIdIn(productCodes);
        LOG.info("inventoryList : {}", inventoryList);

        if (!inventoryList.isEmpty()) {
            throw new RuntimeException(String.format("Inventory already available for product  : %s", inventoryList));
        }
    }

}
