package gdg.hongik.mission.controller;

import gdg.hongik.mission.dto.request.CartAddRequest;
import gdg.hongik.mission.dto.response.CartListResponse;
import gdg.hongik.mission.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
@CrossOrigin("*")
public class CartController {

    private final CartService cartService;

    //장바구니 담기
    @PostMapping
    public ResponseEntity<Void> putCart(@RequestBody CartAddRequest request) throws Exception {

        cartService.addItemToCart(request.getUserId(), request.getProductId(), request.getQuantity());

        return ResponseEntity.created(URI.create("/carts/" + request.getUserId())).build();
    }
}
