package gdg.hongik.mission.dto.request;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductCreateRequest {


    private String name;

    private Long stock;

    private Long price;

    public ProductCreateRequest( String name,Long stock, Long price) {
        this.name = name;
        this.stock = stock;
        this.price = price;
    }
}
