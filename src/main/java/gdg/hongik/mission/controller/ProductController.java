package gdg.hongik.mission.controller;

import gdg.hongik.mission.dto.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @GetMapping("/products")
    public List<productSearchResponseDto> searchProducts(@RequestParam String name) {
        return List.of(new productSearchResponseDto(1L, "apple", 1000, 100));
    }

    @PostMapping("/products")
    public void createProducts(@RequestBody productCreateRequestDto createDto) {}

    @PatchMapping ("/products/{productId}")
    public productAddResponseDto addStock(@PathVariable Long productId, @RequestBody productAddRequestDto addDto) {
        return new productAddResponseDto("apple", 110);
    }

    @DeleteMapping ("/products/{productId}")
    public productDeleteResponseDto deleteProducts(@PathVariable Long productId, @RequestBody productDeleteRequestDto deleteDto) {
        return new productDeleteResponseDto("orange", 20);
    }
}