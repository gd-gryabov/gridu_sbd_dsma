package gridu.dsma.productservice.controller;

import gridu.dsma.productservice.model.Product;
import gridu.dsma.productservice.service.ProductService;
import gridu.dsma.productservice.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/product", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProductController {
    private final ProductService productService;

    @GetMapping("/id/{id}")
    public @ResponseBody
    Product productById(@PathVariable String id) {
        log.info("Return product with id={}", id);
        return productService.findProductById(id)
            .orElseThrow(() -> new ProductNotFoundException("No product found with id=" + id));
    }

    @GetMapping("/sku/{sku}")
    public @ResponseBody
    Collection<Product> productByCode(@PathVariable String sku) {
        log.info("Return product with sku={}", sku);
        return productService.findProductsBySku(sku);
    }
}
