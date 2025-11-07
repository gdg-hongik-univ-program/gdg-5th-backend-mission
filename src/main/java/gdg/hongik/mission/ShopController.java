package gdg.hongik.mission;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/products")
public class ShopController {

    private final ShopService shopService;

    //재고 검색
    @GetMapping("/products/{name}")
    //product타입을 반환한다
    public ResponseEntity<Product> searchProduct(@RequestParam String productName) throws Exception{
        Product product = shopService.searchProduct(productName); //서비스로 넘긴다 -> 이것도 과제인가?
        return ResponseEntity.ok(null); //200 요청 성공
    }

    //재고 구매
    @PostMapping("/orders")
    //order타입 반환
    public ResponseEntity<Void> createOrder(@RequestBody OrderCreateRequest request) throws Exception{
//        Order newOrder = shopService.createOrder(request);
//        OrderResponse response = new OrderResponse(newOrder);
//        //
        return ResponseEntity.created(URI.create("/orders/")).build(); //201 리소스 생성 성공
    }

    //재고 등록 - 에러 반환 해야됨!!
    @PostMapping("/admin/products")
    public ResponseEntity<Void> createProduct(@RequestBody ProductCreateRequest request) throws Exception{
        if(true){
            Product product = new Product(request.getName(), request.getPrice(), request.getStock()); //재고 객체 생성
            return ResponseEntity.created(URI.create("/product/")).build(); //201 리소스 생성 성공
        }
        else{
            return ResponseEntity.noContent().build();
        }



    }

    //재고 추가
    @PatchMapping("/admin/products/{name}/stock") // PATCH /admin/products/{name}/stock?quantity=int
    public ResponseEntity<Product> updateStock(@PathVariable String productName, @RequestParam Integer quantity) {
        //재고 수정, 정보 반환
        shopService.updateProduct(productName, quantity);
        return ResponseEntity.noContent().build(); //이거 아님
    }


    //물품 삭제
    @DeleteMapping("/admin/products")
    public ResponseEntity<Void> deleteProduct(@RequestParam List<String> productName) throws Exception{
        shopService.deleteProduct(productName);
        return ResponseEntity.noContent().build(); //204 요청 성공 응답 없음
    }
}
