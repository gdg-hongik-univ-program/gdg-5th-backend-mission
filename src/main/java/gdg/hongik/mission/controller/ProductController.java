package gdg.hongik.mission.controller;



import gdg.hongik.mission.dto.Product;
import gdg.hongik.mission.dto.request.ProductCreateRequest;
import gdg.hongik.mission.dto.request.ProductDeleteRequest;
import gdg.hongik.mission.dto.request.ProductUpdateRequest;
import gdg.hongik.mission.dto.response.ProductDeleteResponse;
import gdg.hongik.mission.dto.response.ProductGetResponse;
import gdg.hongik.mission.dto.response.ProductUpdateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("products")
@Tag(name ="Product-Controller" ,description = "Product API")
public class ProductController {


    HashMap<Long,Product> repository = new HashMap<>();
    private Long idSequence = 1L;

    @GetMapping("/{name}")
    @Operation(summary = "재고 검색", description = "재고 정보를 검색합니다. 사용자 관리자 모두 사용 가능")
    @ApiResponse(responseCode = "200",content = @Content(mediaType = "application/json",
            examples = {@ExampleObject(
                    name="재고 검색 성공",
                    value = """
                            {
                                "id" : 1,
                                "name" : "apple",
                                "price" : 1000,
                                "stock" : 100                            
                            }
                            """
            )}))
    public ResponseEntity<?> getProduct(@PathVariable String name){

        for (HashMap.Entry<Long, Product> entry : repository.entrySet()) {
            Product product = entry.getValue();
            if (name.equals(product.getName())) {

                ProductGetResponse result = ProductGetResponse.builder()
                        .id(entry.getKey()) // 여기서 id 주입
                        .name(product.getName())
                        .price(product.getPrice())
                        .stock(product.getStock())
                        .build();

                return ResponseEntity.ok(result);
            }
        }
        throw new RuntimeException("존재하지 않는 상품명입니다: " + name);
    }



    @PostMapping
    @Operation(summary = "재고 등록", description = "재고를 등록합니다. 관리자만 사용 가능")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(
                            name = "재고 등록 성공",
                            value = """
                            {
                                "name" : "apple",
                                "stock" : 100,
                                "price" : 1000
                            }
                            """)
                    })),
            @ApiResponse(responseCode = "400", content = @Content( mediaType = "application/json",
                    examples = {@ExampleObject(
                            name = "재고등록 실패 - 이미 존재하는 상품",
                            value = """
                            {
                                "message" : "이미 존재하는 상품입니다"
                            }
                            """
                    )
                    }))
    })
    public ResponseEntity<Void> createProduct(@RequestBody ProductCreateRequest request){

        for(Product product : repository.values()){
            if(request.getName().equals(product.getName())){
                throw new RuntimeException("이미 존재하는 상품명입니다: " + request.getName());

            }

        }

        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .stock(request.getStock())
                .build(); //builder 패턴

        repository.put(idSequence++,product);

        return ResponseEntity.created(URI.create("products")).build();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "재고 수정" , description = "재고를 수정합니다, 관리자만 가능")
    @ApiResponse(responseCode = "200" , content = @Content(mediaType ="application/json",
        examples = {@ExampleObject(
                name ="재고 수정 완료",
                value = """
                        {
                            "name"  : "apple",
                            "stock"   : 110
                        }
                        """
        )}))
    public ResponseEntity<ProductUpdateResponse> updateProduct(
            @PathVariable Long id, @RequestBody ProductUpdateRequest request){

        Product product = repository.get(id);

        System.out.println(product.toString());
        product.setStock(product.getStock() + request.getCnt());

        ProductUpdateResponse result = ProductUpdateResponse.builder()
                .stock(product.getStock())
                .name(product.getName())
                .build();

        return ResponseEntity.ok(result);
    }


    @DeleteMapping
    @Operation(summary = "물품 삭제", description = "관리자만 사용 가능")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
            examples = {@ExampleObject(
                    name = "물품 삭제 성공",
                    value = """
                            {
                                "items" :
                                [
                                    {
                                     "name" : "milk",
                                     "stock" : 20
                                    },
                                    {
                                     "name" : "orange",
                                     "sotck" : 20
                                    }
                                ]
                            }
                            """
            )}))
    public ResponseEntity<ProductDeleteResponse> deleteProduct(
            @RequestBody ProductDeleteRequest request){


        // 요청으로부터 삭제할 상품 이름 목록
        List<String> deleteNames = request.getName();

        // 응답 객체 생성
        ProductDeleteResponse response = new ProductDeleteResponse();
        List<ProductDeleteResponse.Item> remainingItems = new ArrayList<>();

        // repository는 HashMap<Long, Product> 타입
        Iterator<HashMap.Entry<Long, Product>> it = repository.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry<Long, Product> entry = it.next();
            Product product = entry.getValue();

            // 삭제할 이름과 일치하면 repository에서 제거
            if (deleteNames.contains(product.getName())) {
                it.remove();
                continue;
            }

            // 남은 물품 정보를 응답 리스트에 추가
            ProductDeleteResponse.Item item = new ProductDeleteResponse.Item();
            item.setName(product.getName());
            item.setStock(product.getStock());
            remainingItems.add(item);
        }

        // 응답 구성
        response.setItems(remainingItems);

        return ResponseEntity.ok(response);
    }


}
