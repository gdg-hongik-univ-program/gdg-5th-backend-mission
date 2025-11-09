package gdg.hongik.mission.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;



@Getter
@Builder
@AllArgsConstructor
public class ProductUpdateResponse {


    private String name;
    private Long stock;
}
