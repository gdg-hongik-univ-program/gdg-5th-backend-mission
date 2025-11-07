//주문 정보
package gdg.hongik.mission;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //주문 아이디가 필요한가?
//    private List<OrderProduct> products = new ArrayList<>();
    private Long totalPrice;

        public Order(Long totalPrice,  List<OrderProduct> products ) {
            this.totalPrice = totalPrice;
//            this.products = products;
        }
//주문과 주문상품의 관계...
}
