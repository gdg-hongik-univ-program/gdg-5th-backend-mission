package gdg.hongik.mission.service;

import gdg.hongik.mission.dto.*;
import gdg.hongik.mission.entity.Product;
import gdg.hongik.mission.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service // 서비스니까
public class ProductService {
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 상품명을 기준으로 개별 상품을 조회한다.
     *
     * @param name 조회할 상품명
     * @return 조회된 상품 정보를 담은 ProductResponse
     */
    public ProductResponse getProductByName(String name) {
        Product p = productRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 상품 이름입니다."));

        return new ProductResponse(
                p.getId(),
                p.getName(),
                p.getPrice(),
                p.getStock()
        ); // DTO롤 반환하자. DTO에 생성자를 만들 수도 있을 것 같다.
    }

    /**
     * 전체 상품 목록을 반환한다.
     * 엔티티를 직접 반환한다. Admin이 아니므로 외부 노출용 DTO가 필요하지 않다.
     *
     * @return DB에 저장된 모든 상품 엔티티 리스트
     */
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    /**
     * 구매 로직, 장바구니에 담긴 상품들을 구매한다.
     * 전달된 PurchaseRequest 내 아이템 목록을 for 루프로 순회하며
     * Id로 상품을 조회하고 개수 * 가격으로 소비금액을 계산하여
     * 상품 재고 차감 후 DB에 반영한다.
     *
     * @param request 구매할 상품 ID 및 수량 목록을 담은 DTO
     * @return 총 구매액 및 구매된 아이템 목록을 포함한 DTO
     */
    public PurchaseResponse purchase(PurchaseRequest request) {
        int totalPrice = 0;
        List<PurchaseItemResponse> resultItems = new ArrayList<>();

        for (PurchaseItemRequest item : request.getItems()) {
            Product product = productRepository.findById(item.getId())
                    .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

            // 주문 금액을 계산한다.
            int spend = product.getPrice() * item.getQuantity();
            totalPrice += spend;

            // 응답용 아이템을 생성한다.
            resultItems.add(new PurchaseItemResponse(
                    product.getName(),
                    item.getQuantity(),
                    spend
            ));

            // 재고를 차감한다... 명세에는 없지만 해야 될 것 같다.
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
        }

        return new PurchaseResponse(totalPrice, resultItems);
    }

}
