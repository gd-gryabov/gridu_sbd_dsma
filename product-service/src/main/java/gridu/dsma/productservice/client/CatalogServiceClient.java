package gridu.dsma.productservice.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import gridu.dsma.productservice.model.CatalogResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogServiceClient {
    private final RestTemplate restTemplate;

    private static final String CATALOG_API_PATH = "http://catalog-service/catalog";

    @HystrixCommand
    public Optional<CatalogResponse> getProductById(String id) {
        ResponseEntity<CatalogResponse> responseEntity =
            restTemplate.getForEntity(CATALOG_API_PATH + "/id/{id}", CatalogResponse.class, id);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return Optional.ofNullable(responseEntity.getBody());
        }

        return Optional.empty();
    }

    @HystrixCommand
    public List<CatalogResponse> getProductsBySku(String sku) {
        ResponseEntity<List<CatalogResponse>> responseEntity =
            restTemplate.exchange(CATALOG_API_PATH + "/sku/{sku}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CatalogResponse>>() {},
                sku);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        }

        return Collections.emptyList();
    }

}
