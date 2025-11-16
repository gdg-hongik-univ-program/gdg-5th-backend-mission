package gdg.hongik.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseItemRequest {
    private Long id; // 구매할 상품의 ID
    private int quantity; // 얼마나 구매할 것인가.
}
