package gdg.hongik.mission.dto.response;

import gdg.hongik.mission.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RemainProductsResponse { // 💡 [수정 1] 클래스 선언은 'class' 키워드와 '{'로 시작해야 합니다.

    // 💡 [수정 2] 필드 선언은 클래스 내부에서 세미콜론(;)으로 끝나야 합니다.
    private List<ProductResponse> remainingProducts;
    private int count;

    /**
     * 정적 팩토리 메서드를 사용하여 Product 리스트로부터 DTO를 생성
     */
    public static RemainProductsResponse from(List<Product> products) {
        List<ProductResponse> productResponses = products.stream()
                .map(ProductResponse::of)
                .toList();

        return new RemainProductsResponse(
                productResponses,
                productResponses.size()
        );
    }
}