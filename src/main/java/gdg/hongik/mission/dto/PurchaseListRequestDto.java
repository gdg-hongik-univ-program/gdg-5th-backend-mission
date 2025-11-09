package gdg.hongik.mission.dto;

import lombok.Getter;

import java.util.List;
@Getter
public class PurchaseListRequestDto {
    private List<PurchaseRequestDto> itemsToPurchase;
}
