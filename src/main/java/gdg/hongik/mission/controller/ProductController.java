    package gdg.hongik.mission.controller;

    import gdg.hongik.mission.dto.request.ProductDeleteRequest;
    import gdg.hongik.mission.dto.response.ProductResponse;
    import gdg.hongik.mission.dto.request.ProductCreateRequest;
    import gdg.hongik.mission.dto.response.RemainProductsResponse;
    import gdg.hongik.mission.service.ProductService;
    import gdg.hongik.mission.dto.request.StockUpdateRequest;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import java.net.URI;

    /**
     * 상품(Product) 관련 HTTP 요청을 처리하는 컨트롤러
     * 상품 검색, 등록, 재고 수정, 삭제 등 관리 기능
     *
     * @author hyeoniss
     * @since 2025-11-16
     * @see ProductService
     */
    @RestController
    @RequiredArgsConstructor
    public class ProductController {

        private final ProductService productService;

        /**
         * 특정 상품의 정보를 이름으로 검색
         *
         * @param productName 검색할 상품의 이름
         * @return 검색된 상품 정보를 담은 {@code ProductResponse}, HTTP 200 OK 상태 코드
         * @throws Exception 상품을 찾을 수 없는 경우 등 서비스 계층에서 발생한 예외
         */
        @GetMapping("/products")
        public ResponseEntity<ProductResponse> searchProduct(@RequestParam(value = "productName") String productName) throws Exception{
            ProductResponse searchProduct = productService.searchProduct(productName);
            return ResponseEntity.ok(searchProduct);
        }

        /**
         * 새로운 상품을 등록 (관리자 권한 필요?)
         *
         * @param request 상품 생성을 위한 데이터 DTO
         * @return 생성된 상품의 URI, HTTP 201 Created 상태 코드를 포함하는 {@code ResponseEntity}
         * @throws Exception 이미 존재하는 상품명일 경우 등 서비스 계층에서 발생한 예외
         */
        @PostMapping("/admin/products")
        public ResponseEntity<Void> createProduct(@RequestBody ProductCreateRequest request) throws Exception{

            ProductResponse newProduct = productService.createProduct(request);
            Long productId = newProduct.getId();
            return ResponseEntity.created(URI.create("products/" + productId)).build();
        }

        /**
         * 특정 상품의 재고 수량을 추가 (관리자 권한 필요?)
         * {@code updateProduct}를 호출하여 재고를 증가
         *
         * @param request 재고를 추가할 상품 이름과 수량 정보를 담은 DTO
         * @return 수정된 상품 정보를 담은 {@code ProductResponse}, HTTP 200 OK 상태 코드
         * @throws Exception 상품을 찾을 수 없는 경우 등 서비스 계층에서 발생한 예외
         */
        @PatchMapping("/admin/products/{productId}/stock")
        public ResponseEntity<ProductResponse> updateProduct(@PathVariable(value = "productId") Long productId,
                                                             @RequestBody StockUpdateRequest request) throws Exception {
            // 재고를 증가시키고 정보 반환
            ProductResponse updateProduct = productService.updateProduct(productId, request.getQuantity());
            return ResponseEntity.ok(updateProduct);
        }


        /**
         * 하나 이상의 상품을 이름으로 삭제 (관리자 권한 필요?)
         *
         * @param request 삭제할 상품 이름 리스트
         * @return HTTP 204 No Content 상태 코드를 포함하는 {@code ResponseEntity}
         * @throws Exception 삭제 처리 중 발생한 예외
         */
        @DeleteMapping("/admin/products")
        public ResponseEntity<RemainProductsResponse> deleteProducts(
                @RequestBody ProductDeleteRequest request) {

            // 서비스 호출 시 DTO 내부의 리스트를 전달
            RemainProductsResponse response = productService.deleteProducts(request.getProductIds());
            return ResponseEntity.ok(response);
        }
    }