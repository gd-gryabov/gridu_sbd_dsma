package gridu.dsma.productservice.client;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import gridu.dsma.productservice.model.InventoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceClient {
    private final RestTemplate restTemplate;

    private static final String INVENTORY_API_PATH = "http://inventory-service";

    @HystrixCommand(fallbackMethod = "getDefaultInventoryByProductIds",
        commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
        })
    public Map<String, Integer> getInventoryByProductIds(List<String> productIds) {
        String ids = String.join(",", productIds);

        ResponseEntity<List<InventoryResponse>> itemResponseEntity =
            restTemplate.exchange(INVENTORY_API_PATH + "/inventory/{ids}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<InventoryResponse>>() {},
                ids);

        simulateRandomDelay();

        if (itemResponseEntity.getStatusCode().is2xxSuccessful()) {
            List<InventoryResponse> items = itemResponseEntity.getBody();
            if (items != null && !items.isEmpty()) {
                return items.stream().collect(Collectors
                    .toMap(InventoryResponse::getId, InventoryResponse::getAvailableQuantity));
            }
        }

        return Collections.emptyMap();
    }

    private Map<String, Integer> getDefaultInventoryByProductIds(List<String> productIds) {
        log.info("Returning default map for ids={}", productIds);
        return productIds.stream().collect(Collectors.toMap(Function.identity(), value -> 0, (i1, i2) -> 0));
    }

    private void simulateRandomDelay() {
        int delay = 0;
        try {
            delay = RandomUtils.nextInt(4000);
            log.info("Simulated delay={}", delay);
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            log.info("Interrupted simulateRandomDelay with delay={}", delay);
            Thread.currentThread().interrupt();
        }
    }
}
