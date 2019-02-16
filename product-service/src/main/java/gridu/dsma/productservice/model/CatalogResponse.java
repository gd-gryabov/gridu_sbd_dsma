package gridu.dsma.productservice.model;

import lombok.Data;

@Data
public class CatalogResponse {
    private String id;
    private String sku;
    private String name;
    private String description;
    private Double price;
}
