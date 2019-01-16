package gridu.dsma.catalogservice.services;

import com.google.common.collect.ImmutableList;
import gridu.dsma.catalogservice.entities.Product;
import gridu.dsma.catalogservice.exceptions.ProductNotFoundException;
import gridu.dsma.catalogservice.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product findProductById(String id) {
        return productRepository.findById(id).orElseThrow(() ->
            new ProductNotFoundException("No product found with id=" + id));
    }

    public Collection<Product> findProductsBySku(String sku) {
        return productRepository.findBySku(sku).stream()
            .collect(ImmutableList.toImmutableList());
    }
}
