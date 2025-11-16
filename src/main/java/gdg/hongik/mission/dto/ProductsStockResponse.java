package gdg.hongik.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProductsStockResponse {
    private List<ProductResponse> totalstock;
}
