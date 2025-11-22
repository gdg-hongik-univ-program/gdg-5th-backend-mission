//OrderCreateRequest에 속할 주문 상품 정보 리스트
package gdg.hongik.mission.dto.request;

import lombok.Getter;

/**
 * 주문 생성 요청({@link OrderCreateRequest})에 포함되는 개별 상품의 정보 DTO
 *
 * @author hyeoniss
 */
@Getter
public class OrderProductRequest {
    /** 주문 상품의 이름 */
    private String name;
    /** 주문 수량 */
    private int quantity;
    /** 주문 시점의 상품 가격 */
    private int price;

    /**
     * 생성자
     *
     * @param name 상품 이름
     * @param quantity 주문 수량
     * @param price 상품 단가
     */
    public OrderProductRequest(String  name, int quantity, int price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
}