package gdg.hongik.mission.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "구매할 개별 물품 정보")
public class PurchaseOrderItem {
    @Schema(description = "물건 이름", example = "apple")
    private String name;
    @Schema(description = "구매 개수", example = "3")
    private Integer quantity;

    public PurchaseOrderItem(){}
    public String getName(){return name;}
    public void setName(String name){this.name = name;}
    public Integer getQuantity(){return quantity;}
    public void setQuantity(Integer quantity){this.quantity = quantity;}
}
