package gridu.dsma.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    private String id;
    private String sku;
    private String name;
    private String description;
    private double price;
    private int qty;
    private boolean inStock;
}
