package com.ambraj.inventoryservice.repository;

import com.ambraj.inventoryservice.dto.InventoryResponse;
import com.ambraj.inventoryservice.entity.Inventory;
import jakarta.persistence.criteria.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findBySkuCodeIn(List<String> skuCodes);
}
