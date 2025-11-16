package gdg.hongik.mission.service;


import gdg.hongik.mission.dto.Product;
import gdg.hongik.mission.dto.request.OrderCreateRequest;
import gdg.hongik.mission.dto.request.ProductCreateRequest;
import gdg.hongik.mission.dto.request.ProductUpdateRequest;
import gdg.hongik.mission.dto.response.OrderCreateResponse;
import gdg.hongik.mission.dto.response.ProductDeleteResponse;
import gdg.hongik.mission.dto.response.ProductGetResponse;
import gdg.hongik.mission.dto.response.ProductUpdateResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 
 * 서비스 계층 비즈니스 로직을 담당하는 계층
 * 
 */
@Service
public class ProductService {

    /**
     * DB 연결을 안했으르모 임시 메모리 사용
     */
    static HashMap<Long, Product> repository = new HashMap<>();
    private Long idSequence = 1L;


    /**
     * 재고 검색 비즈니스 로직
     * 람다 함수로 값 찾음 
     * 널 or 실제값 반환
     * @param productName
     * @return
     */
    @Transactional
    public ProductGetResponse getProduct (String productName){

        //코드 중복임
        Map.Entry<Long,Product> find = repository.entrySet().stream()
                .filter(p -> productName.equals(p.getValue().getName()))
                .findFirst()
                .orElse(null);


        ProductGetResponse pg = ProductGetResponse.builder()
                .id(find.getKey())
                .stock(find.getValue().getStock())
                .price(find.getValue().getPrice())
                .name(find.getValue().getName())
                .build();

        return pg;
    }


    /**
     * 이름으로 찾기 서비스 계층
     * 이름과 매칭되는 값 존재 = true
     * 이름과 매칭되는 값 없음 = false
     * @param name
     * @return
     */
    @Transactional
    public boolean findByName(String name){
        
        //코드 중복임
        Map.Entry<Long,Product> find = repository.entrySet().stream()
                .filter(p -> name.equals(p.getValue().getName()))
                .findFirst()
                .orElse(null);


        if( find == null ) {

            return false;

        }


        return true;
    }

    /**
     * 재고 생성 비즈니스
     *
     * @param request
     */
    public void postProduct(ProductCreateRequest request) {

        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .stock(request.getStock())
                .build(); //builder 패턴

        repository.put(idSequence++,product);
    }

    /**
     * id로 재고 검색
     * name으로 재고 검색과 다른점은 
     * 재고가 없으면 여기서 예외처리 함
     * @param id
     * @return
     */
    public Long findById (Long id) {

        Map.Entry<Long, Product> ex = repository.entrySet().stream()
                .filter(p -> id.equals(p.getKey()))
                .findFirst()
                .orElse(null);
        if( ex == null){
            throw new RuntimeException("존재하지 않는 id입니다");
        }

        return ex.getKey();

    }

    /**
     * 물건 업데이트 서비스 로직
     * @param findId
     * @param request
     * @return
     */
    public ProductUpdateResponse updateProduct(
            Long findId,
            ProductUpdateRequest request) {

        Product product = repository.get(findId);

        product.updateProduct(request.getCnt());

        ProductUpdateResponse pr = ProductUpdateResponse.builder()
                .name(product.getName())
                .stock(product.getStock())
                .build();

        return pr;

    }

    /**
     * 물품 삭제 서비스 로직
     * @param deleteNames
     * @return
     */
    public ProductDeleteResponse deleteProducts(List<String> deleteNames) {

        // 응답 객체와 남은 물품 리스트 초기화
        ProductDeleteResponse response = new ProductDeleteResponse();
        List<ProductDeleteResponse.Item> remainingItems = new ArrayList<>();

        // repository.entrySet()에 대한 안전한 순회 및 제거를 위해 Iterator 사용
        Iterator<Map.Entry<Long, Product>> it = repository.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Long, Product> entry = it.next();
            Product product = entry.getValue();

            // 삭제할 이름 목록에 포함되면 저장소에서 제거 (it.remove())
            if (deleteNames.contains(product.getName())) {
                it.remove();
            } else {
                // 남은 물품 정보를 응답 리스트에 추가
                ProductDeleteResponse.Item item = new ProductDeleteResponse.Item();
                item.setName(product.getName());
                item.setStock(product.getStock());
                remainingItems.add(item);
            }
        }

        // 응답 구성 및 반환
        response.setItems(remainingItems);
        return response;
    }

    /**
     * Order 컨트롤러에서 연결
     * 물품 차감 , 이외의 에러처리 구현 x
     * @param request
     * @return
     */
    public static OrderCreateResponse orderCreate(OrderCreateRequest request) {

        List<OrderCreateResponse.Item> or = new ArrayList<>();
        Long totalPrice = 0L;
        for(OrderCreateRequest.Item item : request.getItems()){
            String itemName = item.getName();
            Map.Entry<Long,Product> find = repository.entrySet().stream()
                    .filter(p -> itemName.equals(p.getValue().getName()))
                    .findFirst()
                    .orElse(null);
            //구매할 상품 갯수
            Long n = item.getCnt();


            OrderCreateResponse.Item pg = OrderCreateResponse.Item.builder()
                    .price(find.getValue().getPrice() * n)
                    .name(find.getValue().getName())
                    .cnt(n)
                    .build();
            or.add(pg);
            totalPrice += find.getValue().getPrice() * n;
        }

        OrderCreateResponse result = new OrderCreateResponse() ;
        result.setTotalPrice(totalPrice);
        result.setItem(or);


        return result;
    }


}
