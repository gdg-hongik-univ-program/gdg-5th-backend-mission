package gdg.hongik.mission.controller;

import gdg.hongik.mission.dto.*;
import gdg.hongik.mission.dto.*;
import gdg.hongik.mission.entity.Product;
import gdg.hongik.mission.service.AdminProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/products")
public class AdminProductController {

    private final AdminProductService adminProductService;
    public AdminProductController(AdminProductService adminProductService) {
        this.adminProductService = adminProductService;
    }

    @PostMapping // 새로운 상품을 등록한다.
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest request) { // 기본적으로 한 번에 하나의 등록, 수정만 가능하다고 하자.
        // createProductRequest는 DTO에서 자세히 구현한다.
        Product product = new Product(); // 새 상품 객체 생성
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        Product saved = adminProductService.create(product); // create 서비스 호출
        // 이미 존재하는 상품에 대한 예외는 서비스 계층에서 처리한다.
        return ResponseEntity.ok(saved);
    }

    @PatchMapping("/stock")
    public ResponseEntity<ProductResponse> addStock(@RequestBody AddStockRequest request) { // dto에서 구현해야 할 내용.
        Product updated = adminProductService.addStock(request.getId(), request.getAddStock());

        ProductResponse response = new ProductResponse( // ProductResponse DTO를 이용해 최종 재고 수를 반환한다.
                updated.getId(),
                updated.getName(),
                updated.getPrice(),
                updated.getStock()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping// 입력은 한 개 이상의 물품일 수 있다고 했다.
    public ResponseEntity<ProductsStockResponse> deleteProducts(@RequestBody DeleteProductsRequest request) {
        List<Product> totalstock = adminProductService.deleteProducts(request.getIds()); // 이러면 서비스 계층에서 삭제 처리 후 findAlL() 결과를 넘겨줄 것이다.
        List<ProductResponse> responses = totalstock.stream()
                .map(p -> new ProductResponse(p.getId(), p.getName(), p.getPrice(), p.getStock()))
                .toList();
        return ResponseEntity.ok(new ProductsStockResponse(responses));
    } // 예외 처리는 서비스에서 담당한다. Delete는 메시지만 반환하므로 MessageResponse DTO도 구현해줘야 한다.
}
