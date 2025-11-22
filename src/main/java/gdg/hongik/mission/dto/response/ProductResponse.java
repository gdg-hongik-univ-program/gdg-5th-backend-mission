package gdg.hongik.mission.dto.response;

import gdg.hongik.mission.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 상품 정보 조회의 결과 또는 상품 생성/수정 후 클라이언트에게 반환되는 응답 DTO
 *
 * @author hyeoniss
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    /** 상품 ID */
    private Long id;
    /** 상품 이름 */
    private String name;
    /** 상품 단가 */
    private int price;
    /** 현재 재고 수량 */
    private int stock;

    /**
     * {@link Product} 객체를 받아 {@code ProductResponse} DTO로 변환하는 정적 팩토리 메서드
     *
     * @param product DB에서 조회되거나 저장된 상품 객체
     * @return 상품 정보 응답 DTO
     */
    public static ProductResponse of(Product product) {
        ProductResponse response = new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getQuantity());

        return response;
    }
}