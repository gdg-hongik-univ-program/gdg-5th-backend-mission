package gdg.hongik.mission.service;

import gdg.hongik.mission.dto.response.ProductResponse;
import gdg.hongik.mission.dto.request.ProductCreateRequest;
import gdg.hongik.mission.dto.response.RemainProductsResponse;

import java.util.List;

/**
 * 상품 관련 Service 계층을 정의하는 인터페이스
 * 상품 조회, 등록, 수정, 삭제 및 재고 관리 기능을 외부에 노출시킴
 *
 * @author hyeoniss
 */
public interface ProductService {

    /**
     * 상품 이름으로 상품 정보를 검색
     *
     * @param name 검색할 상품명
     * @return 검색된 상품 정보를 담은 응답 DTO
     */
    ProductResponse searchProduct(String name);

    /**
     * 새로운 상품을 등록
     *
     * @param request 상품 생성을 위한 데이터 DTO
     * @return 생성된 상품 정보를 담은 응답 DTO
     */
    ProductResponse createProduct(ProductCreateRequest request);

    /**
     * 특정 상품의 재고 수량을 수정
     *
     * @param id 재고를 수정할 상품 아이디
     * @param quantity 수정할 수량
     * @return 수정된 상품 정보를 담은 응답 DTO
     */
    ProductResponse updateProduct(Long id, int quantity);

    /**
     * 지정된 이름의 상품들을 삭제
     *
     * @param ids 삭제할 상품 이름 리스트
     */
    RemainProductsResponse deleteProducts(List<Long> ids);

    /**
     * 주문 등으로 인해 특정 상품의 재고를 감소
     * {@code OrderService}에서 호출됨
     *
     * @param id 재고를 감소시킬 상품아이디
     * @param quantity 감소시킬 수량
     * @throws RuntimeException 재고가 부족할 경우 등
     */
    void decreaseStock(Long id, int quantity);

}