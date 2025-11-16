package gdg.hongik.mission.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddStockRequest {
    private Long id; // 재고를 늘릴 상품 ID
    private int addStock; // 증가할 재고량
}
