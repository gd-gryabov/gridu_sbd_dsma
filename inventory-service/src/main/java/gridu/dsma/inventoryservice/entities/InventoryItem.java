package gridu.dsma.inventoryservice.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "inventory")
public class InventoryItem {
    @Id
    private String id;
    @Column(name = "quantity")
    private Integer availableQuantity = 0;
}
