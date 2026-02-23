package com.ecom.inventory.mapper;

import com.ecom.inventory.dto.InventoryRequestDto;
import com.ecom.inventory.dto.InventoryRespondDto;
import com.ecom.inventory.entity.Inventory;
import org.springframework.stereotype.Component;

@Component
public class InvantoryMapper {
    public Inventory toInventory(InventoryRequestDto dto) {
        Inventory inventory = new Inventory();
        inventory.setSkuCode(dto.getSkuCode());
        inventory.setQuantity(dto.getQuantity());
        return inventory;
    }
    public InventoryRespondDto toResponse(Inventory inventory) {
        InventoryRespondDto inventoryRespondDto = new InventoryRespondDto();
        inventoryRespondDto.setSkuCode(inventory.getSkuCode());
        inventoryRespondDto.setInStock(inventory.getQuantity() > 0);
        inventoryRespondDto.setAvailablQuantity(inventory.getQuantity());
        return inventoryRespondDto;
    }
}
