package gdg.hongik.mission.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OrderCreateResponse {

    @Schema(description = "상품 전체 가격", example = "20000")
    private Long totalPrice;

    @Schema(description = "상품 이름", example = "apple")
    private String name;

    @Schema(description = "상품 갯수", example = "20")
    private Long cnt ;

    @Schema(description = "상품 가격", example = "1000")
    private Long price ;

    public OrderCreateResponse(String name, Long cnt) {
        this.totalPrice = 40000L;
        this.name = name;
        this.cnt = cnt;
        this.price = 100L;
    }
}
