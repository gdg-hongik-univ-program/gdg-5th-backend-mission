package gdg.hongik.mission.controller;

import gdg.hongik.mission.entity.Product;
import gdg.hongik.mission.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping // 이름으로 상품을 검색한다.
    public Product getProduct(@RequestParam String name) {
        return productService.getProductByName(name);
    }

    @PostMapping("/purchase") // 장바구니에 담긴 상품들을 구매한다.
    public Map<String, Object> purchase(@RequestBody Map<String, Object> body) { // 여러 개일 수 있으니 Map 해시를 사용.
        return productService.purchase(body);
    }


}
