package gdg.hongik.mission.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 장바구니에 상품 항목을 추가하기 위한 요청 DTO
 * @author hyeoniss
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class CartAddRequest {

    /** 장바구니 소유자 ID */
    private Long userId;

    /** 추가할 상품 ID */
    private Long productId;

    /** 추가할 수량 */
    private int quantity;
}
