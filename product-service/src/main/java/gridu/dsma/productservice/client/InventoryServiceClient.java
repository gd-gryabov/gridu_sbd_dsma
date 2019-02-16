package gridu.dsma.productservice.client;


import gridu.dsma.productservice.model.InventoryResponse;
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
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceClient {
    private final RestTemplate restTemplate;

    private static final String INVENTORY_API_PATH = "http://inventory-service";

    public Map<String, Integer> getInventoryByProductIds(List<String> productIds) {
        String ids = String.join(",", productIds);

        ResponseEntity<List<InventoryResponse>> itemResponseEntity =
            restTemplate.exchange(INVENTORY_API_PATH + "/inventory/{ids}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<InventoryResponse>>() {},
                ids);

        if (itemResponseEntity.getStatusCode().is2xxSuccessful()) {
            List<InventoryResponse> items = itemResponseEntity.getBody();
            if (items != null && !items.isEmpty()) {
                return items.stream().collect(Collectors
                    .toMap(InventoryResponse::getId, InventoryResponse::getAvailableQuantity));
            }
        }

        return Collections.emptyMap();
    }
}
