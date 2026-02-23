package com.ecom.order.client;

import com.ecom.order.dto.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "inventory-service")
public interface InventoryFeignClient  {
    @GetMapping("/api/inventory/skucode")
    InventoryResponse inInStock(@PathVariable String skucode);
}
