package gridu.dsma.inventoryservice.controllers;

import gridu.dsma.inventoryservice.entities.InventoryItem;
import gridu.dsma.inventoryservice.repositories.InventoryItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/inventory", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class InventoryController {
    private final InventoryItemRepository inventoryItemRepository;

    @GetMapping("/{idList}")
    public @ResponseBody
    Collection<InventoryItem> findInventoryByProductCode(@PathVariable List<String> idList) {
        log.info("Getting inventory info for ids={}", idList);
        return inventoryItemRepository.findAllById(idList);
    }
}
