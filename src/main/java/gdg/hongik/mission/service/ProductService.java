package gdg.hongik.mission.service;

import gdg.hongik.mission.dto.*;
import org.springframework.stereotype.Service;

/**
 * controller에서 받은 요청을 처리하고 다시 돌려줍니다
 * 캡슐화
 */
@Service
public class ProductService {
    public ProductDto searchStock(String name) {
        System.out.println("name: " + name);

        // 실제라면 DB에서 상품 찾아서 반환
        ProductDto product = new ProductDto();
        // product.setName(name);
        // product.setStock(100);
        return product;
    }

    public PurchaseResponse buyItems(PurchaseOrderRequest request) {
        System.out.println("구매 요청 목록 크기: " + request.getItems().size() + "개");

        // 총 금액 계산, 재고 차감 등의 비즈니스 로직은 여기에 들어감
        // 예시: int totalPrice = request.getItems().stream().mapToInt(...).sum();

        return new PurchaseResponse(); // 계산 결과 담아 반환
    }

    public void registerProduct(ProductRegistrationRequest request) {
        // 🟢 실제 등록 로직 수행
        System.out.println("새 물품 등록 요청: "
                + request.getName() + ", 가격: " + request.getPrice() + ", 재고: " + request.getStock());

        // 실제라면 여기에 DB 저장 로직이 들어감
        // ex) productRepository.save(new Product(request.getName(), request.getPrice(), request.getStock()));
    }

    public StockAddResponse addStock(Integer id, StockAddRequest request) {
        // 🟢 실제 재고 추가 로직 수행
        System.out.println("ID " + id + "번 물품에 재고 " + request.getAddStock() + " 추가 요청");

        // 실제라면 DB에서 해당 상품을 찾아 재고를 업데이트
        // ex)
        // Product product = productRepository.findById(id);
        // product.setStock(product.getStock() + request.getAddStock());
        // productRepository.save(product);

        // 응답 DTO 생성
        ProductDto updatedItem = new ProductDto();
        StockAddResponse response = new StockAddResponse();
        response.setItem(updatedItem);
        return response;
    }

    public DeleteResponse deleteProducts(DeleteRequest request) {
        // 🟢 실제 삭제 로직 수행
        System.out.println("삭제 요청된 물품 목록: " + request.getNames());

        // 예시 로직: DB에서 상품 삭제
        // productRepository.deleteAllByNameIn(request.getNames());

        // 삭제 후 남은 상품 목록 조회해서 응답 DTO에 담기
        DeleteResponse response = new DeleteResponse();
        // response.setRemainingItems(...);

        return response;
    }
}

