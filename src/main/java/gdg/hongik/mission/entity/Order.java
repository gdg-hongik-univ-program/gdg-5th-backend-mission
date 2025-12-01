//주문 정보
package gdg.hongik.mission.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 주문 정보를 나타내는 객체
 * 여러 개의 주문 상품 정보({@link OrderProduct})를 포함
 *
 * @author hyeoniss
 */
@Entity
@Table(name = "ORDERS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    /**
     * 주문에 포함된 주문 상품 목록
     * {@code Order}와 {@code OrderProduct}는 1:N 관계
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    /**
     * 해당 주문의 총 금액 (주문 상품 목록의 금액의 총합)
     */
    private int totalPrice;

    /**
     * 새로운 주문 객체를 생성하는 private 생성자=
     * {@link #createOrder(List)} 정적 팩토리 메서드를 통해 호출됨
     *
     * @param orderProducts 해당 주문에 포함될 주문 상품 리스트
     */
    private Order(List<OrderProduct> orderProducts) {

        this.addOrderProducts(orderProducts);
        this.totalPrice = orderProducts.stream()
                .mapToInt(OrderProduct::calculateSubprice)
                .sum();
    }

    /**
     * 주문을 생성하는 정적 팩토리 메서드
     *
     * @param orderProducts 주문에 포함될 주문 상품({@code OrderProduct}) 리스트
     * @return 생성된 {@code Order} 객체
     */
    public static Order createOrder(List<OrderProduct> orderProducts) {

        return new Order(orderProducts);
    }

    /**
     * 주문 상품 리스트를 현재 주문 객체에 추가하고, 동시에 주문 상품 객체에 현재 주문(this)을 설정
     * 양방향 관계 설정을 처리
     *
     * @param orderProducts 추가할 주문 상품 리스트
     */
    private void addOrderProducts(List<OrderProduct> orderProducts) {

        for (OrderProduct op : orderProducts) { // Order : OrderProduct = 1 : N
            this.orderProducts.add(op); // orderProducts 리스트에 새 OrderProduct 객체 op를 추가
            op.setOrder(this); // OrderProduct에게 Order를 참조하도록 설정 (외래키)
        }
    }

}