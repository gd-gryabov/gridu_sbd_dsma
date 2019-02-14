package gridu.dsma.catalogservice.service;

import com.google.common.collect.ImmutableList;
import gridu.dsma.catalogservice.entity.Product;
import gridu.dsma.catalogservice.exception.ProductNotFoundException;
import gridu.dsma.catalogservice.repository.CatalogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogService {
    private final CatalogRepository catalogRepository;

    public Optional<Product> findProductById(String id) {
        return catalogRepository.findById(id);
    }

    public Collection<Product> findProductsBySku(String sku) {
        return catalogRepository.findBySku(sku).stream()
            .collect(ImmutableList.toImmutableList());
    }
}
