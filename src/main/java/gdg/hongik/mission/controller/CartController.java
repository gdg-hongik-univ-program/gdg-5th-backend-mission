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

        cartService.addItemToCart(
                request.getUserId(),
                request.getProductId(),
                request.getQuantity()
        );

        CartListResponse cartList = cartService.getCartList(request.getUserId());

        return ResponseEntity.created(URI.create("/carts/" + request.getUserId()))
                .body(cartList);
    }
}
