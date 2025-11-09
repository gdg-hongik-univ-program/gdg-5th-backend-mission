//OrderCreateRequest에 속할 주문 상품 정보 리스트
package gdg.hongik.mission;

import lombok.Getter;

@Getter
public class OrderProductRequest {
    private String productName; // 어떤 상품인지
    private int quantity;
    private int price;

    public OrderProductRequest(String productName, int quantity, int price) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }
}
