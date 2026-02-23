package com.ecom.inventory.controller;

import com.ecom.inventory.dto.InventoryRequestDto;
import com.ecom.inventory.dto.InventoryRespondDto;
import com.ecom.inventory.service.InventoryServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryServices inventoryServices;

    public InventoryController(InventoryServices inventoryServices) {
        this.inventoryServices = inventoryServices;
    }

    // ✅ Check Inventory by SKU
    @GetMapping("/{skuCode}")
    public ResponseEntity<InventoryRespondDto> checkInventory(
            @PathVariable String skuCode) {

        InventoryRespondDto response =
                inventoryServices.checkInventory(skuCode);

        return ResponseEntity.ok(response);
    }

    // ✅ Add Inventory
    @PostMapping
    public ResponseEntity<String> addInventory(
            @RequestBody InventoryRequestDto inventoryRequestDto) {

        inventoryServices.addInventory(inventoryRequestDto);

        return new ResponseEntity<>(
                "Inventory Added Successfully",
                HttpStatus.CREATED
        );
    }
}