package gdg.hongik.mission.dto;

import lombok.Getter;

@Getter
public class productAddResponseDto {
    private String name;
    private int stock;

    public productAddResponseDto(String name, int stock) {
        this.name = name;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }
}
