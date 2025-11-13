package gdg.hongik.mission.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "기존 물품 재고 추가 요청")
public class StockAddRequest {
    @Schema(description = "추가할 재고 수", example = "20")
    private Integer addStock;

    public StockAddRequest() {
    }

    public StockAddRequest(Integer addStock) {
        this.addStock = addStock;
    }

    public Integer getAddStock() {
        return addStock;
    }

    public void setAddStock(Integer addStock) {
        this.addStock = addStock;
    }
}
