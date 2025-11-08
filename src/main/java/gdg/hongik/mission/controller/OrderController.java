package gdg.hongik.mission.controller;



import gdg.hongik.mission.dto.request.OrderCreateRequest;
import gdg.hongik.mission.dto.response.OrderCreateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.springframework.web.servlet.function.ServerResponse.created;

@RestController
@RequestMapping("order")
@Tag(name="Order",description = "Order API")
public class OrderController {

    @PostMapping
    @Operation(summary = "주문 등록", description = "주문 정보를 생성합니다")
    @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json",
            examples = {@ExampleObject(
                    name = "주문정보 생성 성공",
                    value = """
                    {
                        "totalPrice" : 40000,
                        "cart" : 
                        [
                            {
                                "name" : "apple",
                                "cnt" : 20,
                                "price" : 1000
                            },
                            {
                                "name"  : "water",
                                "cnt"   : 20,
                                "price" : 1000
                            }
                        ]
                    }
                    """)
    }))
    public ResponseEntity<OrderCreateResponse> createOrder(@RequestBody OrderCreateRequest request){

        OrderCreateResponse result = new OrderCreateResponse(
                request.getName(),
                request.getCnt()
        );

        return ResponseEntity.ok(result);


    }
}
