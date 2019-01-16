package gridu.dsma.catalogservice.services;


import gridu.dsma.catalogservice.models.ProductInventoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceClient {
    private final RestTemplate restTemplate;

    private static final String INVENTORY_API_PATH = "http://inventory-service/api/";


    public Optional<ProductInventoryResponse> getProductInventoryByCode(String productCode) {
        ResponseEntity<ProductInventoryResponse> itemResponseEntity =
            restTemplate.getForEntity(INVENTORY_API_PATH + "inventory/{code}",
                ProductInventoryResponse.class,
                productCode);

        if (itemResponseEntity.getStatusCode() == HttpStatus.OK) {
            return Optional.ofNullable(itemResponseEntity.getBody());
        }

        return Optional.empty();
    }

    public List<ProductInventoryResponse> getProductInventoryLevels() {
        ResponseEntity<List<ProductInventoryResponse>> itemResponseEntity = restTemplate.exchange(INVENTORY_API_PATH + "inventory",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<ProductInventoryResponse>>() {
            }
        );

        if (itemResponseEntity.getStatusCode() == HttpStatus.OK) {
            return itemResponseEntity.getBody();
        }

        return Collections.emptyList();
    }
}
