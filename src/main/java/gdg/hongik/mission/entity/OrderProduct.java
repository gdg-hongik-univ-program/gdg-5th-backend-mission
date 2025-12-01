// 구매 완료 상태의 상품
package gdg.hongik.mission.entity;

import gdg.hongik.mission.dto.request.OrderProductRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 시에 생성되는 상품 정보 객체
 * 상품({@code Product})과 분리된 존재
 *
 * @author hyeoniss
 */
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderProductId;

    /** 주문 상품 이름 */
    private String orderProductName;
    /** 주문 수량 */
    private int quantity;
    /** 주문 시점의 상품 단가 */
    private int price;

    /**
     * 해당 주문 상품이 속한 주문({@link Order})입니다.
     * {@code OrderProduct}와 {@code Order}는 N:1 관계
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;


    /**
     * 새로운 주문 상품 객체를 생성하는 private 생성자
     *
     * @param name 상품 이름
     * @param quantity 주문 수량
     * @param price 주문 시점의 상품 가격
     */
    private OrderProduct(String name, int quantity, int price) {
        this.orderProductName = name;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * 주문 상품 객체를 생성하는 정적 팩토리 메서드
     * DTO의 정보를 받아 객체를 생성
     *
     * @param request 주문 상품 생성 요청 DTO
     * @return 생성된 {@code OrderProduct} 객체
     */
    public static OrderProduct create(OrderProductRequest request) { // OrderProduct 객체 생성
        return new OrderProduct(request.getProductName(), request.getQuantity(), request.getPrice());
    }

    /**
     * 해당 주문 상품이 속하는 주문 객체를 설정(양방향)
     *
     * @param order 설정할 주문 객체
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * 주문 상품의 총 금액(단가 * 수량)을 계산하여 반환
     *
     * @return 주문 상품의 총 금액
     */
    public int calculateSubprice() {
        return  quantity * price;
    }
}