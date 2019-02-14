package gridu.dsma.inventoryservice.repository;

import gridu.dsma.inventoryservice.entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, String> {

}
