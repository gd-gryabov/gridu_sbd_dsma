package gridu.dsma.productservice.service;

import gridu.dsma.productservice.client.CatalogServiceClient;
import gridu.dsma.productservice.client.InventoryServiceClient;
import gridu.dsma.productservice.model.CatalogResponse;
import gridu.dsma.productservice.model.InventoryResponse;
import gridu.dsma.productservice.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final CatalogServiceClient catalogClient;
    private final InventoryServiceClient inventoryClient;

    public Optional<Product> findProductById(String id) {
        Optional<CatalogResponse> productCatalog = catalogClient.getProductById(id);

        if (productCatalog.isPresent()) {
            Map<String, Integer> inventory = inventoryClient.getInventoryByProductIds(Collections.singletonList(id));

            if (!inventory.isEmpty()) {
                return Optional.of(merge(productCatalog.get(), inventory.get(id)));
            }
        }

        return Optional.empty();

    }

    public Collection<Product> findProductsBySku(String sku) {
        List<CatalogResponse> catProducts = catalogClient.getProductsBySku(sku);
        List<String> productIds = catProducts.stream().map(CatalogResponse::getId).collect(Collectors.toList());
        Map<String, Integer> invProducts = inventoryClient.getInventoryByProductIds(productIds);

        return catProducts.stream()
            .map(p -> merge(p, invProducts.getOrDefault(p.getId(), 0)))
            .collect(Collectors.toList());
    }

    private Product merge(CatalogResponse catalog, int qty) {
        return new Product(
            catalog.getId(),
            catalog.getSku(),
            catalog.getName(),
            catalog.getDescription(),
            catalog.getPrice(),
            qty,
            qty > 0
        );
    }
}
