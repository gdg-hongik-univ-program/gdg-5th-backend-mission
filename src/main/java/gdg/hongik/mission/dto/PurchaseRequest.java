package gdg.hongik.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequest {
    private List<PurchaseItemRequest> items; // 장바구니에 담긴 물건을 여러 개일 것이니.
}
