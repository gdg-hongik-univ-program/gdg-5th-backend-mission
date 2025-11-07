// 주문 상품 개별 정보
package gdg.hongik.mission;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //id가 필요한가

    private String productName;  // 물건 이름
    private Integer quantity; // 구매 수량
    private Long price; //상품 가격
    private Long subPrice; // 상품별 금액

    //주문 상품 정보 생성자
    public OrderProduct(String productName, int quantity, Long price) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.subPrice = price * quantity; //상품별 총금액 = 개수 * 가격
    }
}
