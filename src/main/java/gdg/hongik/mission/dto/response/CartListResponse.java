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

}