package gdg.hongik.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class productSearchResponseDto {
    private Long id;
    private String name;
    private int price;
    private int stock;
}
