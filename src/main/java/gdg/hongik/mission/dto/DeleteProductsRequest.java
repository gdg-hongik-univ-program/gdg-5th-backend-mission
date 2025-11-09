package gdg.hongik.mission.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeleteProductsRequest {
    private List<Long> ids;
}
