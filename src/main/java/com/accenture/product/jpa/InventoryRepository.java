package com.accenture.product.jpa;

import com.accenture.product.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProductId(String productCode);

    List<Inventory> findAllByProductIdIn(List<String> productIds);
}