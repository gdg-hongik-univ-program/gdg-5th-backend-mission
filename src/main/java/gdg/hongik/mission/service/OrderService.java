package gdg.hongik.mission.service;

import gdg.hongik.mission.dto.response.CartListResponse;
import gdg.hongik.mission.dto.response.OrderCreateResponse;
import org.springframework.stereotype.Service;

/**
 * 주문 관련 Service 계층을 정의하는 인터페이스
 *
 * @author hyeoniss
 */
@Service
public interface OrderService {

    /**
     * 사용자의 장바구니에 담긴 상품을 기반으로 주문을 생성
     * 재고 감소 및 장바구니 정리 포함
     *
     * @param userId 주문을 생성할 사용자의 ID
     * @return 생성된 주문을 담은 {@code OrderCreateResponse} DTO
     */
    OrderCreateResponse createOrderFromCart(Long userId);
//    Long addItemToCart(Long userId, Long productId, int quantity);
//    CartListResponse getCartList(Long userId);
}