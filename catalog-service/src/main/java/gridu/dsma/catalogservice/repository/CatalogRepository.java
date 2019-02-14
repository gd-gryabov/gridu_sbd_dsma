package gridu.dsma.catalogservice.repository;

import gridu.dsma.catalogservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CatalogRepository extends JpaRepository<Product, String> {
    List<Product> findBySku(String code);
}
