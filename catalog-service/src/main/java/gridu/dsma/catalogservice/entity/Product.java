package gridu.dsma.catalogservice.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "catalog")
public class Product {
    @Id
    private String id;

    @Column(nullable = false)
    private String sku;

    private String name;

    @Column(length = 9216)
    private String description;

    private double price;
}
