package gdg.hongik.mission.dto.response;


import gdg.hongik.mission.entity.OrderProduct;
import lombok.Getter;

/**
 * 주문 생성 응답({@link OrderCreateResponse})에 포함되는 개별 상품의 상세 정보 DTO
 *
 * @author hyeoniss
 */
@Getter
public class OrderProductResponse {

    private Long orderproductId;
    private String orderProductName;
    private int quantity;
    /** 상품별 금액 (단가 * 수량) */
    private int subPrice;

    /**
     * 생성자
     *
     * @param id 주문 상품 ID
     * @param name 상품 이름
     * @param quantity 주문 수량
     * @param subPrice 상품별 소계 금액
     */
    public OrderProductResponse(Long id, String name, int quantity, int subPrice) {
        this.orderproductId = id;
        this.orderProductName = name;
        this.quantity = quantity;
        this.subPrice = subPrice;
    }

    /**
     * {@link OrderProduct} 객체를 받아 {@code OrderProductResponse} DTO로 변환하는 정적 팩토리 메서드
     *
     * @param op 주문 상품 객체
     * @return 변환된 주문 상품 응답 DTO
     */
    public static OrderProductResponse of(OrderProduct op) {
        Long id = op.getOrderProductId();
        String name = op.getOrderProductName();
        int quantity = op.getQuantity();
        int subPrice = op.calculateSubprice(); // 여기서 subPrice 계산

        return new OrderProductResponse(id, name, quantity, subPrice);
    }
}