package gdg.hongik.mission.controller;

import gdg.hongik.mission.dto.request.CartAddRequest;
import gdg.hongik.mission.dto.response.CartListResponse;
import gdg.hongik.mission.repository.CartRepository;
import gdg.hongik.mission.service.CartService;
import gdg.hongik.mission.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;


@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartListResponse> putCart(@RequestBody CartAddRequest request) throws Exception {

        // 1. 서비스 호출: 장바구니에 상품을 추가합니다. (기존 로직 유지)
        // Long cartItemId = ... (CartItem ID를 반환받는 대신, Cart 자체를 반환받도록 서비스를 수정하거나,
        //                         별도의 조회 메서드를 호출합니다.)
        cartService.addItemToCart(
                request.getUserId(),
                request.getProductId(),
                request.getQuantity()
        );

        // 💡 [추가] 장바구니 추가 후, 해당 사용자의 최신 장바구니 목록을 조회합니다.
        // OrderService에 새로운 메서드를 추가해야 합니다. (예: getCartList)
        CartListResponse cartList = cartService.getCartList(request.getUserId());

        // 2. HTTP 201 Created와 함께 장바구니 목록을 응답 본문에 반환
        // (POST 메서드이므로, 201 Created 상태 코드를 유지하는 것이 적절합니다.)
        return ResponseEntity.created(URI.create("/carts/" + request.getUserId()))
                .body(cartList); // 💡 DTO를 Body에 담아 반환
    }
}
