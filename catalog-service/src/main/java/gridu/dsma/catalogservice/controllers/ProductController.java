package gridu.dsma.catalogservice.controllers;


import gridu.dsma.catalogservice.entities.Product;
import gridu.dsma.catalogservice.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/id/{id}")
    public @ResponseBody Product productById(@PathVariable String id) {
        log.info("Return product with id={}", id);
        return productService.findProductById(id);
    }

    @GetMapping("/sku/{sku}")
    public @ResponseBody Collection<Product> productByCode(@PathVariable String sku) {
        log.info("Return product with sku={}", sku);
        return productService.findProductsBySku(sku);
    }

}
