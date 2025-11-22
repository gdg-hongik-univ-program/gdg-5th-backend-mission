//주문 상품 정보 리스트
package gdg.hongik.mission.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 새로운 주문 생성을 위한 클라이언트 요청 DTO
 * 여러 개의 상품 정보를 리스트 형태로 포함
 *
 * @author hyeoniss
 * @since 2025-11-16
 */
@Getter
@NoArgsConstructor
public class OrderCreateRequest {

    /** 주문할 상품 정보 목록 */
    private List<OrderProductRequest> products;

}