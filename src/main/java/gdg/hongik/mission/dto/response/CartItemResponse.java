package gdg.hongik.mission.dto.response;

import gdg.hongik.mission.entity.CartItem;
import gdg.hongik.mission.entity.Product; // Product 엔티티 접근을 위해 필요
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 장바구니 항목 개별 정보를 위한 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {

    // 장바구니 항목 ID (삭제/수정 시 특정 항목 지정을 위해 필요)
    private Long cartItemId;

    // 상품 정보
    private Long productId;
    private String productName;
    private int unitPrice;       // 단가

    // 장바구니 정보
    private int quantity;        // 담은 수량
    private int itemTotalPrice;  // 항목별 총 가격 (단가 * 수량)

    /**
     * CartItem 엔티티를 받아 DTO로 변환하는 정적 팩토리 메서드
     */
    public static CartItemResponse of(CartItem item) {
        Product product = item.getProduct(); // CartItem이 Product를 참조한다고 가정
        int unitPrice = product.getPrice();
        int quantity = item.getQuantity();

        return new CartItemResponse(
                item.getId(),
                product.getId(),
                product.getName(),
                unitPrice,
                quantity,
                unitPrice * quantity // 총액 계산
        );
    }
}