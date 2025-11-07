package gdg.hongik.mission.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Schema(description = "주문 요청 DTO")
@Data
public class OrderCreateRequest {

    @Schema(description = "상품 이름", example = "apple")
    private String name ;

    @Schema(description = "상품 갯수", example = "20")
    private Long cnt;

    public OrderCreateRequest(String name, Long cnt) {
        this.name = name;
        this.cnt = cnt;
    }


}
