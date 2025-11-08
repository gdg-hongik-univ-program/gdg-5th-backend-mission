package gdg.hongik.mission.controller;



import gdg.hongik.mission.dto.Product;
import gdg.hongik.mission.dto.request.ProductCreateRequest;
import gdg.hongik.mission.repository.ProductRespository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("products")
@Tag(name ="Product-Controller" ,description = "Product API")
public class ProductController {

    private ProductRespository productRespository;

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
    public ResponseEntity<Product> getProduct(@PathVariable String name){
        Product product = new Product();

        return ResponseEntity.ok(product);
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

        //Product existProduct = ProdcutRepository.findByName(request.getName());

        return ResponseEntity.created(URI.create("products")).build();
    }

    @PatchMapping
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
    public ResponseEntity<Void> updateProduct(String t){



        return ResponseEntity.ok().build();
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

    public ResponseEntity<Void> deleteProduct(String t){
        return ResponseEntity.ok().build();
    }


}
