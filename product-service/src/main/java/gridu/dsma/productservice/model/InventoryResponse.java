package gridu.dsma.productservice.model;

import lombok.Data;

@Data
public class InventoryResponse {
    private String id;
    private Integer availableQuantity = 0;
}