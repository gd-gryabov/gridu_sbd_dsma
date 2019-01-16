package gridu.dsma.catalogservice.repositories;

import gridu.dsma.catalogservice.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findBySku(String code);
}
