package gdg.hongik.mission.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductCreateRequest {

    private String name;
    private Long stock;
    private Long price;

}
