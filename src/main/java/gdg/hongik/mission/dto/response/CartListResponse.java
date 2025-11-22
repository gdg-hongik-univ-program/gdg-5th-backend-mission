package gdg.hongik.mission.dto.response;

import gdg.hongik.mission.entity.CartItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 장바구니 전체 목록 및 총액 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartListResponse {

    /** 장바구니 항목 리스트 */
    private List<CartItemResponse> items;

    /** 장바구니 전체 총 금액 */
    private int totalPrice;

    /**
     * List<CartItem> 엔티티를 받아 CartListResponse로 변환하는 정적 팩토리 메서드
     */
    public static CartListResponse from(List<CartItem> cartItems) {

        // 1. 개별 CartItem 엔티티를 CartItemResponse DTO로 변환
        List<CartItemResponse> itemResponses = cartItems.stream()
                .map(CartItemResponse::of)
                .collect(Collectors.toList());

        // 2. 전체 총액 계산
        int totalPrice = itemResponses.stream()
                .mapToInt(CartItemResponse::getItemTotalPrice)
                .sum();

        // 3. 최종 DTO 생성
        return new CartListResponse(itemResponses, totalPrice);
    }
}