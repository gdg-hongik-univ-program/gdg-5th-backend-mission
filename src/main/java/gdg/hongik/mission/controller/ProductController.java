package gdg.hongik.mission.controller;

import gdg.hongik.mission.dto.*; // 모든 DTO를 import

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "쇼핑몰 API", description = "재고 검색, 구매, 등록, 추가, 물품삭제를 지원")
@RestController
@RequestMapping()
public class ProductController{
    // 1. 재고검색, GET /product?name=string
    @Operation( // 각 API에 대한 설명을 추가할 수 있음
            summary = "재고 검색",
            description = "이름 기준으로 물품 재고 검색",
            responses = { // response = HTTP 상태코드에 대해서 우리가 반환해주는 값
                    @ApiResponse(responseCode = "200", description = "검색성공",
                            // 200 OK가 되었을떄 어떤 응답을 보여줄 것인지
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                            // content = 무엇을 정의 하는가  -> ApiResonse나 @RequestBody 같은 어노테이션 내부에 위치함
                            // schema = @Schema -> content 가 따르는 데이터 모델 정의
                            // @Schema = 데이터 객체의 구조(필드이름, 타입등 설명)
                            // implement = 스키마가 자바의 어떤 클래스의 구조를 따르는지
                    )
            }
    )
    // GetMapping = 메소드위에 붙어서 메소드를 실행하는역할
    // Opration = 기능 설명 제공
    // parameter = 변수
    @GetMapping("/products?=name=string")
    public ProductDto searchStock(
            @Parameter(description = "검색할 물건의 이름", example = "apple")
            @RequestParam String name) {
        return new ProductDto();
    }



// 2. 재고구매 POST /products
@Operation(
        summary = "재고구매",
        description = "물품을 구매하고 총 금액과 개별 사용 금액을 나타냄",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody( // 💡 @RequestBody 어노테이션 사용
                description = "구매할 물품 목록과 수량",
                required = true,
                content = @Content(
                        schema = @Schema(implementation = PurchaseOrderRequest.class)
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "구매 성공 및 결과 반환",
                        content = @Content(
                                schema = @Schema(implementation = PurchaseResponse.class))
                )
        }
)
@PostMapping("/products")
public PurchaseResponse buyItems(
        @org.springframework.web.bind.annotation.RequestBody
        PurchaseOrderRequest request
) {
    System.out.println("구매 요청 목록: " + request.getItems().size() + "개");

    return new PurchaseResponse();
}

// 3.재고등록 POST /products
@Operation(
        summary = "새 물품 재고 등록",
        description = "새로운 물품의 이름, 가격, 초기 재고를 등록합니다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "등록할 물품의 상세 정보",
                required = true,
                content = @Content(
                        schema = @Schema(implementation = ProductRegistrationRequest.class)
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "등록 성공 (응답 본문 없음)"
                )
        }
)
@PostMapping("/products/register")
public void registerProduct(
        @org.springframework.web.bind.annotation.RequestBody
        ProductRegistrationRequest request
) {
    System.out.println("새 물품 등록 요청: " + request.getName() + ", 재고: " + request.getStock());

    // 실제 등록 로직 수행
    // 반환할 것이 없으므로 return 문은 생략하거나 명시적인 return; 사용
}

// 4. 재고 추가 PATCH /products/{id}
@Operation(
        summary = "기존 물품 재고 추가",
        description = "특정 ID를 가진 물품의 재고를 지정된 수량만큼 늘립니다.",
        // URL 경로에 들어가는 파라미터 정의
        parameters = {
                @Parameter(name = "id", description = "재고를 추가할 물품의 고유 ID", example = "20")
        },
        // INPUT 정의
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "추가할 재고 수량",
                required = true,
                content = @Content(
                        schema = @Schema(implementation = StockAddRequest.class)
                )
        ),
        // OUTPUT 정의
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "재고 추가 성공 및 결과 반환",
                        content = @Content(
                                schema = @Schema(implementation = StockAddResponse.class) // 응답 DTO 사용
                        )
                )
        }
)
@PatchMapping("/products/{id}")
public StockAddResponse addStock(
        @PathVariable Integer id, // URL 경로에서 ID를 받음
        @org.springframework.web.bind.annotation.RequestBody
        StockAddRequest request // 요청 본문에서 추가 수량을 받음
) {
    System.out.println("ID " + id + " 물품에 재고 " + request.getAddStock() + " 추가 요청");

    ProductDto updatedItem = new ProductDto();
    StockAddResponse response = new StockAddResponse();
    response.setItem(updatedItem);

    return response;
}

// 5. 물품 삭제 (DELETE /prodcts)
@Operation(
        summary = "물품 삭제",
        description = "요청 본문의 이름 목록을 받아 해당 물품들을 시스템에서 삭제합니다.",
        // INPUT 정의
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "삭제할 물품 이름 목록",
                required = true,
                content = @Content(
                        schema = @Schema(implementation = DeleteRequest.class) // 입력 DTO 사용
                )
        ),
        // OUTPUT 정의
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "삭제 성공 및 잔여 물품 정보 반환",
                        content = @Content(
                                schema = @Schema(implementation = DeleteResponse.class) // 응답 DTO 사용
                        )
                )
        }
)
@DeleteMapping("/products")
public DeleteResponse deleteProducts(
        @org.springframework.web.bind.annotation.RequestBody
        DeleteRequest request // 삭제 요청 DTO를 받음
) {
    System.out.println("삭제 요청된 물품 목록: " + request.getNames());

    DeleteResponse response = new DeleteResponse();
    // 남아있는 물품 목록을 조회하여 response에 설정

    return response;
}

}// 전체 닫는 괄호