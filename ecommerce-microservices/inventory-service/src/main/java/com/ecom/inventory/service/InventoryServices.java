package com.ecom.inventory.service;

import com.ecom.inventory.dto.InventoryRequestDto;
import com.ecom.inventory.dto.InventoryRespondDto;
import com.ecom.inventory.entity.Inventory;
import com.ecom.inventory.exception.InventoryNotFoundException;
import com.ecom.inventory.mapper.InvantoryMapper;
import com.ecom.inventory.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryServices {

    private final InventoryRepository inventoryRepository;
    private final InvantoryMapper invantoryMapper;
    public InventoryServices(InventoryRepository inventoryRepository, InvantoryMapper invantoryMapper) {
        this.inventoryRepository = inventoryRepository;
        this.invantoryMapper = invantoryMapper;
    }

    public InventoryRespondDto checkInventory(String skuCode) {
        Inventory foundInventory = inventoryRepository.findByskuCode(skuCode).orElseThrow(
                () -> new InventoryNotFoundException(skuCode)
        );
        return invantoryMapper.toResponse(foundInventory);
    }

    public void addInventory(InventoryRequestDto inventoryRequestDto) {
        Inventory newInventory = invantoryMapper.toInventory(inventoryRequestDto);
        newInventory = inventoryRepository.save(newInventory);
    }
}
