package gdg.hongik.mission.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductGetResponse {

    private Long id ;
    private String name;
    private Long price;
    private Long stock;

    @Builder
    public ProductGetResponse(Long id,Long stock, Long price, String name) {
        this.id = id;
        this.stock = stock;
        this.price = price;
        this.name = name;
    }

}
