package com.accenture.product.service;

import com.accenture.product.dto.InventoryDTO;
import com.accenture.product.dto.ProductDTO;
import com.accenture.product.entity.Inventory;
import com.accenture.product.jpa.InventoryRepository;
import com.accenture.product.util.InventoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.accenture.product.enums.UrlEnum.PRODUCTS_URL;
import static com.accenture.product.enums.UrlEnum.PRODUCT_FORMAT;

@Service
public class InventoryService {

    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InventoryRepository invRepository;

    @Autowired
    private InventoryValidationService invValidationService;

    @Autowired
    private InventoryRemoteService invRemoteService;

    @Autowired
    private InventoryUtil inventoryUtil;

    public List<InventoryDTO> getInventories() {
        List<Inventory> inventories = invRepository.findAll();
        List<ProductDTO> productDTO = invRemoteService.invokeGetProductService(PRODUCTS_URL.value());
        return inventoryUtil.convertDTOS(inventories, productDTO);
    }


    public InventoryDTO getInventory(Long code) {
        Inventory inventory = invRepository.findById(code).orElseThrow(() -> new RuntimeException("No Inventory found"));
        String productUrl = String.format(PRODUCT_FORMAT.value(), PRODUCTS_URL.value(), inventory.getProductId());
        List<ProductDTO> products = invRemoteService.invokeGetProductService(productUrl);
        return inventoryUtil.convertDto(inventory, products.get(0));
    }

    public InventoryDTO findInventoryByProductCode(String productCode) {
        Inventory inventory = invRepository.findByProductId(productCode).orElseThrow(() -> new RuntimeException(String.format("Inventory not found for product : %s", productCode)));
        String productUrl = String.format(PRODUCT_FORMAT.value(), PRODUCTS_URL.value(), inventory.getProductId());
        List<ProductDTO> products = invRemoteService.invokeGetProductService(productUrl);
        ProductDTO product = products.get(0);
        return inventoryUtil.convertDto(inventory, product);
    }

    public List<InventoryDTO> addInventory(List<InventoryDTO> inventories) {
        invValidationService.validateInventoryAvailable(inventories);

        List<Inventory> inventoryList = inventories.stream().map(inventoryUtil::convertEntity).toList();
        this.saveInventories(inventoryList);
        return inventories;
    }

    public InventoryDTO addInventory(InventoryDTO inventory) {
        invValidationService.validateInventoryAvailable(inventory);
        Inventory in = inventoryUtil.convertEntity(inventory);
        saveInventory(in);
        return inventory;
    }

    public List<Inventory> saveInventories(List<Inventory> inventories) {
        return invRepository.saveAll(inventories);
    }

    public Inventory saveInventory(Inventory inventory) {
        return invRepository.save(inventory);
    }

    public String removeInventory(Long code) {
        Inventory inventory = invRepository.findById(code).orElseThrow(() -> new RuntimeException("No Inventory found"));
        invRepository.delete(inventory);
        String productUrl = String.format(PRODUCT_FORMAT.value(), PRODUCTS_URL.value(), inventory.getProductId());
        String response = invRemoteService.invokeRemoveProductService(productUrl);
        return String.format("%n Successfully deleted inventory %s And also %s %n", inventory.getProductId(), response);
    }

    public InventoryDTO updateInventory(Long code, Integer quantity) {
        Inventory inventory = invRepository.findById(code).orElseThrow(() -> new RuntimeException("No Inventory found"));
        if (inventory.getQuantity() < quantity) {
            throw new RuntimeException(String.format("Available stock : %d but expected : %d", inventory.getQuantity(), quantity));
        }
        inventory.setQuantity((inventory.getQuantity() - quantity));
        String productUrl = String.format(PRODUCT_FORMAT.value(), PRODUCTS_URL.value(), inventory.getProductId());
        List<ProductDTO> products = invRemoteService.invokeGetProductService(productUrl);
        ProductDTO prod = products.get(0);
        invRepository.save(inventory);
        return inventoryUtil.convertDto(inventory, prod);
    }

}
