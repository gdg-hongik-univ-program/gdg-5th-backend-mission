package gdg.hongik.mission.dto.request;

import gdg.hongik.mission.common.message.ErrorMessage;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 등록을 위한 클라이언트 요청 DTO.
 *
 * @author hyeoniss
 */
@Getter
@NoArgsConstructor
public class ProductCreateRequest {
    /** 등록할 상품 이름 */
    @NotNull(message = ErrorMessage.PRODUCT_NAME_NOT_NULL)
    @Size(min = 4, max = 20, message = ErrorMessage.PRODUCT_NAME_SIZE)
    private String productName;

    /** 상품 단가 */
    @NotNull(message = "가격은 필수입니다")
    @Min(value = 0, message = "가격은 1원 이상이어야 합니다.")
    @Pattern(regexp = "^\\d+$", message = "가격은 정수 형태로 입력해야 합니다.")
    private int price;

    /** 초기 재고 수량 */
    @NotNull(message = "수량은 필수입니다")
    @Min(value = 0, message = "재고는 1개 이상이어야 합니다.")
    @Pattern(regexp = "^\\d+$", message = "재고 수량은 정수 형태로 입력해야 합니다.")
    private int quantity;

}