package gdg.hongik.mission.dto.request;

import lombok.Getter;

import java.util.List;

@Getter

public class ProductDeleteRequest {

    private List<Long> productIds;
}