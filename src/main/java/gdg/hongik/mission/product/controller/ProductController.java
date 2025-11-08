package gdg.hongik.mission.product.controller;

import gdg.hongik.mission.product.dto.ProductDeleteRequestDto;
import gdg.hongik.mission.product.dto.ProductDto;
import gdg.hongik.mission.product.dto.ProductUpdateDto;
import gdg.hongik.mission.product.dto.PurchaseDto;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
public class ProductController {
    @GetMapping("/products")
    public ProductDto getProduct(@RequestParam String name) {
        return new ProductDto(1L, name, 1000, 100);
    }

    @PostMapping("/purchase")
    public PurchaseDto purchase(@RequestBody Map<String, List<Map<String, Object>>> request) {
        List<Map<String, Object>> items = request.get("items");
        List<PurchaseDto.Item> resultItems = new ArrayList<>();
        int totalAmount = 0;
        for (Map<String, Object> item : items) {
            String name = (String) item.get("name");
            int quantity = (int) item.get("quantity");
            int price = name.equals("apple") ? 1000 : 1000;
            int amount = price * quantity;

            resultItems.add(new PurchaseDto.Item(name, quantity, amount));
            totalAmount += amount;
        }
        return new PurchaseDto(totalAmount, resultItems);
    }

    @PostMapping("/products")
    public ProductDto createProduct(@RequestBody com.example.store.dto.ProductRequestDto request) {
        return new ProductDto(1L, request.getName(), request.getPrice(), request.getStock());
    }

    @PatchMapping("/products/{productId}")
    public Map<String, Integer> addStock(@PathVariable Long productId, @RequestBody ProductUpdateDto request) {
        Map<String, Integer> response = new HashMap<>();
        response.put("stock", 100);
        return response;
    }
    @DeleteMapping("/products")
    public Map<String, Object> deleteProducts(@RequestBody ProductDeleteRequestDto request) {
        List<ProductDto> items = List.of(
                new ProductDto(2L, "banana", 1000, 80),
                new ProductDto(3L, "orange", 1500, 40)
        );

        Map<String, Object> response = new HashMap<>();
        response.put("items", items);
        response.put("totalProducts", items.size());
        return response;
    }

}

