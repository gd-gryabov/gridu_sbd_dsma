package gridu.dsma.inventoryservice.repositories;

import gridu.dsma.inventoryservice.entities.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, String> {

}
