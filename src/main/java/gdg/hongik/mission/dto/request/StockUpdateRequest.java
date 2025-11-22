package gdg.hongik.mission.dto.request;

import lombok.Getter;

/**
 * 상품의 재고 수량 수정을 위한 클라이언트 요청 DTO
 *
 * @author hyeoniss
 */
@Getter
public class StockUpdateRequest {
    /** 재고를 수정할 상품 이름 */
    private String name;
    /** 수정할 수량 */
    private Integer quantity;
}