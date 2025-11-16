package gdg.hongik.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PurchaseResponse {
    private int totalPrice;
    private List<PurchaseItemResponse> item;
}
