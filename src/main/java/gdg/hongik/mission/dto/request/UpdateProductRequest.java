package gdg.hongik.mission.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

/**
 * 상품의 재고 수량 수정을 위한 클라이언트 요청 DTO
 *
 * @author hyeoniss
 */
@Getter
public class UpdateProductRequest {

    /** 수정할 수량 */
    @NotNull(message = "수량은 필수입니다")
    @Min(value = 1, message = "재고는 1개 이상이어야 합니다.")
    private Integer quantity;
}