package gdg.hongik.mission.controller;

import gdg.hongik.mission.dto.response.OrderCreateResponse;
import gdg.hongik.mission.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

/**
 * 주문(Order) 관련 HTTP 요청을 처리하는 컨트롤러
 * 클라이언트의 장바구니 주문 요청을 받아 {@link OrderService}로 위임
 *
 * @author hyeoniss
 * @since 2025-11-16
 * @see OrderService
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@CrossOrigin("*")
public class OrderController
{
    private final OrderService orderService;

    /**
     * 장바구니에 담긴 물건을 구매, 주문을 생성
     * 사용자 정보를 기반으로 해당 사용자의 장바구니 상품 목록을 가져와 주문을 처리
     *
     * @return 생성된 주문 정보, HTTP 201 Created 상태 코드를 포함하는 {@code ResponseEntity}
     * @throws Exception 주문 처리 중 발생 가능한 예외
     */
    @PostMapping
    public ResponseEntity<OrderCreateResponse> createOrderFromCart() throws Exception{

        Long MOCK_USER_ID = 1L; // 테스트용 mock 유저
        OrderCreateResponse newOrder = orderService.createOrderFromCart(MOCK_USER_ID);
        return ResponseEntity.created(URI.create("/orders/" + newOrder.getId()))
                .body(newOrder);
    }


}