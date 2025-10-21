package com.accenture.product.util;

import com.accenture.product.dto.InventoryDTO;
import com.accenture.product.dto.ProductDTO;
import com.accenture.product.entity.Inventory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class InventoryUtil {
    private Random random = new Random();

    public List<Inventory> loadInventories(List<String> prodCodes) {
        return prodCodes.stream().map(this::loadInventory).toList();
    }

    public List<InventoryDTO> convertDTOS(List<Inventory> inventories, List<ProductDTO> products) {
        Map<String, List<ProductDTO>> prodMap = products.stream().collect(Collectors.groupingBy(p -> p.getCode()));
        List<InventoryDTO> invDTOS = inventories.stream().map(in -> convertDto(in, prodMap)).toList();
        return invDTOS;
    }

    private InventoryDTO convertDto(Inventory in, Map<String, List<ProductDTO>> prodMap) {
        ProductDTO product = prodMap.get(in.getProductId()).get(0);
        return convertDto(in, product);
    }

    public InventoryDTO convertDto(Inventory in, ProductDTO product) {
        return InventoryDTO.builder().id(in.getId()).product(product).quantity(in.getQuantity()).build();
    }

    public Inventory convertEntity(InventoryDTO in) {
        return Inventory.builder().id(in.getId()).productId(in.getProduct().getCode()).quantity(in.getQuantity()).build();
    }

    private Inventory loadInventory(String prodCode) {
        return Inventory.builder().productId(prodCode).quantity(random.nextInt(5, 10)).build();
    }
}
