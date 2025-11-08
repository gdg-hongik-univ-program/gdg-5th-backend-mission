package gdg.hongik.mission.product.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class ProductDeleteRequestDto {
    private List<Long> ids;
}
