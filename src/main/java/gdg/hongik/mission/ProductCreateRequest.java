package gdg.hongik.mission;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCreateRequest {
    private String name;
    private Long price;
    private Long stock;

    public ProductCreateRequest(String name, Long price, Long stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
}
