package gdg.hongik.mission.controller;

import gdg.hongik.mission.dto.PurchaseListRequestDto;
import gdg.hongik.mission.dto.PurchaseListResponseDto;
import gdg.hongik.mission.dto.PurchaseResponseDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
    @PostMapping("/orders")
    public PurchaseListResponseDto orders(@RequestBody PurchaseListRequestDto requestDto) {
        PurchaseResponseDto apple = new PurchaseResponseDto("apple", 3, 3000);
        PurchaseResponseDto orange = new PurchaseResponseDto("orange", 5, 10000);

        return new PurchaseListResponseDto(13000, List.of(apple, orange));
    }
}
