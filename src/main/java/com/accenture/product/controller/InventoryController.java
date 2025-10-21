package com.accenture.product.controller;

import com.accenture.product.dto.InventoryDTO;
import com.accenture.product.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public List<InventoryDTO> getInventories(@RequestParam(required = false, defaultValue = "0") Long code) {
        return (code == 0) ? inventoryService.getInventories() : List.of(inventoryService.getInventory(code));
    }

    @GetMapping("/find")
    public InventoryDTO findInventoryByProductId(@RequestParam String productCode) {
        return inventoryService.findInventoryByProductCode(productCode);
    }

    @PostMapping
    public List<InventoryDTO> addInventories(@RequestBody ArrayList<InventoryDTO> inventories) {
        LOG.info("Invoking InventoryController method : inventories size : {}", inventories.size());
        if (inventories.size() == 1) {
            return List.of(inventoryService.addInventory(inventories.get(0)));
        } else {
            return inventoryService.addInventory(inventories);
        }
    }

    @DeleteMapping
    public String removeInventory(@RequestParam Long code) {
        return inventoryService.removeInventory(code);
    }

    @PutMapping
    public InventoryDTO updateInventory(@RequestParam Long code, @RequestParam Integer qty) {
        return inventoryService.updateInventory(code, qty);
    }
}
