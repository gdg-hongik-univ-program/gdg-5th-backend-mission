package gdg.hongik.mission.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductCreateRequest {

    @Schema(description = "상품 이름", example = "apple")
    private String name;

    @Schema(description = "재고", example = "20")
    private Long stock;
    
    @Schema(description = "상품 가격", example = "1000")
    private Long price;


    public ProductCreateRequest( String name,Long stock, Long price) {
        this.name = name;
        this.stock = stock;
        this.price = price;
    }
}
