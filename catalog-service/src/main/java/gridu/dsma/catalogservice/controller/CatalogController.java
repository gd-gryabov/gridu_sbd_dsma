package gridu.dsma.catalogservice.controller;


import gridu.dsma.catalogservice.entity.Product;
import gridu.dsma.catalogservice.exception.ProductNotFoundException;
import gridu.dsma.catalogservice.service.CatalogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping(value = "/catalog", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogService catalogService;

    @GetMapping("/id/{id}")
    public @ResponseBody Product productById(@PathVariable String id) {
        log.info("Return product with id={}", id);
        return catalogService.findProductById(id).orElseThrow(() ->
            new ProductNotFoundException("No product found with id=" + id));
    }

    @GetMapping("/sku/{sku}")
    public @ResponseBody Collection<Product> productByCode(@PathVariable String sku) {
        log.info("Return product with sku={}", sku);
        return catalogService.findProductsBySku(sku);
    }

}
