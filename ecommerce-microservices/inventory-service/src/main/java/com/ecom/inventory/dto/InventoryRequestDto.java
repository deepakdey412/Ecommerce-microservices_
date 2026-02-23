package com.ecom.inventory.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryRequestDto {

    @NotBlank(message = "skuCode is required")
    private String skuCode;
    @Min( value = 0 , message = "Quantity must be 0 or greater")
    @NotBlank(message = "Quantity is required")
    private Integer quantity;
}
