package gdg.hongik.mission;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/products")
public class ShopController {

    private final ShopService shopService;

    //재고 검색
    @GetMapping("/products")
    //product타입을 반환한다
    public ResponseEntity<Product> searchProduct(@RequestParam String productName) throws Exception{
        Product product = shopService.searchProduct(productName); //서비스로 넘긴다
        return ResponseEntity.ok(product); //200 요청 성공
    }

    //재고 구매
    @PostMapping("/orders")
    //order타입 반환
    public ResponseEntity<Void> createOrder(@RequestBody OrderCreateRequest request) throws Exception{
        Order newOrder = shopService.createOrder(request);
        return ResponseEntity.created(URI.create("/orders/")).build(); //201 리소스 생성 성공
    }

    //재고 등록 - 에러 반환 해야됨
    @PostMapping("/admin/products")
    public ResponseEntity<Void> createProduct(@RequestBody ProductCreateRequest request) throws Exception{
        try {
            Product createdProduct = shopService.createProduct(request);
            return ResponseEntity.created(URI.create("/product/" + createdProduct.getProductName())).build(); //201 리소스 생성 성공
        }

        catch (Exception e) { //에러처리
            throw new ResponseStatusException( //HTTP 응답 상태 코드, 오류 메시지 반환 - ResponseEntity와 다름(예외/오류 응답)
                    HttpStatus.CONFLICT, "요청하신 상품은 이미 존재하여 등록할 수 없습니다." //409 상태 충돌
            );
        }
    }

    //재고 추가
    @PatchMapping("/admin/products/{name}/stock")
    public ResponseEntity<Product> updateProduct(@RequestBody StockUpdateRequest request) throws Exception {
        //재고 수정, 정보 반환
        Product updateProduct = shopService.updateProduct(request.getProductName(), request.getQuantity());
        return ResponseEntity.ok(updateProduct);
    }


    //물품 삭제
    @DeleteMapping("/admin/products")
    public ResponseEntity<List> deleteProduct(@RequestParam List<String> productName) throws Exception{
        shopService.deleteProduct(productName);
        return ResponseEntity.noContent().build(); //204 요청 성공 응답 없음
    }
}
