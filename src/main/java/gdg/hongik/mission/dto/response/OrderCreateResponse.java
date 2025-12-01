package gdg.hongik.mission.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import gdg.hongik.mission.entity.Order;
import gdg.hongik.mission.entity.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 주문 생성 성공 후 클라이언트에게 반환되는 응답 DTO
 *
 * @author hyeoniss
 */
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonPropertyOrder({"id", "orderedProducts", "totalPrice"})
public class OrderCreateResponse {
    /** 생성된 주문의 ID */
    private Long Id;
    /** 주문에 포함된 상품들의 정보 리스트 */
    private List<OrderProductResponse> orderedProducts;
    /** 최종 결제 금액 */
    private int totalPrice;


    /**
     * {@link Order} 객체를 받아 {@code OrderCreateResponse} DTO로 변환하는 정적 팩토리 메서드
     * 주문 상품 정보도 {@link OrderProductResponse}로 변환하여 포함
     *
     * @param order DB에서 저장된 주문 객체
     * @return 주문 생성 결과 응답 DTO
     */
    public static OrderCreateResponse of(Order order) {

        List<OrderProductResponse> productResponses = new java.util.ArrayList<>();

        for (OrderProduct orderProduct : order.getOrderProducts()) { // 상품 정보 리스트 순회

            OrderProductResponse productResponse = OrderProductResponse.of(orderProduct); // 리스트 하나하나 OrderProductResponse의 of 메서드로 전달
            productResponses.add(productResponse);
        }

        return new OrderCreateResponse(
                order.getOrderId(),         // Order ID
                productResponses,      // 최종 상품 리스트
                order.getTotalPrice()  // Order 객체에서 계산된 총 금액
        );
    }
}