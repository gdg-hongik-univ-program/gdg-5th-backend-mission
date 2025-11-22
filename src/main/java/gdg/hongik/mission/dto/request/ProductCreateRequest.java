package gdg.hongik.mission.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 등록을 위한 클라이언트 요청 DTO.
 *
 * @author hyeoniss
 */
@Getter
@NoArgsConstructor
public class ProductCreateRequest {
    /** 등록할 상품 이름 */
    private String name;
    /** 상품 단가 */
    private int price;
    /** 초기 재고 수량 */
    private int quantity;

}