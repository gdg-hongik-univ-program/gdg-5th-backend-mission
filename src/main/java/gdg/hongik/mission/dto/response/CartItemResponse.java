package gdg.hongik.mission.dto.response;

import gdg.hongik.mission.entity.CartItem;
import gdg.hongik.mission.entity.Product;
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

    private Long cartItemId;

    private Long productId;
    private String productName;
    private int subPrice;

    private int quantity;
    private int TotalPrice;  // 총 가격

    /**
     * CartItem 엔티티를 받아 DTO로 변환하는 정적 팩토리 메서드
     */
    public static CartItemResponse of(CartItem item) {
        Product product = item.getProduct();
        int subPrice = product.getPrice();
        int quantity = item.getQuantity();

        return new CartItemResponse(
                item.getId(),
                product.getId(),
                product.getName(),
                subPrice,
                quantity,
                subPrice * quantity // 총액 계산
        );
    }
}