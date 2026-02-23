package com.ecom.inventory.exception;

public class InventoryNotFoundException extends RuntimeException {
    public InventoryNotFoundException(String skuCode) {
        super( " Inventory not found exception with the skuCode :  " + skuCode);
    }
}
