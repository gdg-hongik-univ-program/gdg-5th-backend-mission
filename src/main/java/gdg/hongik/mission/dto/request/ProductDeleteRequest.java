package gdg.hongik.mission.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter

public class ProductDeleteRequest {

    @NotNull(message = "삭제할 상품 이름 목록은 필수입니다.") // null 방지
    @NotEmpty(message = "삭제할 상품 이름을 최소 하나 이상 입력해야 합니다.")
    private List<String> productNames;
}