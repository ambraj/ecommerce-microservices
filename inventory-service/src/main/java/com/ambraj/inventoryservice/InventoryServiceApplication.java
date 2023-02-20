package com.ambraj.inventoryservice;

import com.ambraj.inventoryservice.entity.Inventory;
import com.ambraj.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadInventory(InventoryRepository inventoryRepository) {
        return args -> {
            Inventory inventory1 = new Inventory();
            inventory1.setSkuCode("iPhone 13");
            inventory1.setQuantity(5L);

            Inventory inventory2 = new Inventory();
            inventory2.setSkuCode("iPhone 14");
            inventory2.setQuantity(0L);

            Inventory inventory3 = new Inventory();
            inventory3.setSkuCode("Xbox");
            inventory3.setQuantity(1L);

            inventoryRepository.saveAll(List.of(inventory1, inventory2, inventory3));
        };
    }

}
