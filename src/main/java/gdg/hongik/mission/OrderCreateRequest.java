//주문 상품 정보 리스트
package gdg.hongik.mission;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderCreateRequest {
    private List<OrderProductRequest> products; //물건정보는 여러개.....리스트
//    private Long totalPrice; 필요없는거같음

    public OrderCreateRequest(String productName, int quantity, int price) {
        this.products = new ArrayList<>();
    }
}
