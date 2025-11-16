package gdg.hongik.mission.service;

import gdg.hongik.mission.dto.*;
import gdg.hongik.mission.entity.Product;
import gdg.hongik.mission.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminProductService {
    private final ProductRepository productRepository;
    public AdminProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 새로운 상품을 등록한다. 동일한 상품명이 이미 존재하는 경우 예외 발생
     *
     * @param request 생성할 상품의 정보를 담은 DTO
     * @return 생성된 상품 정보를 담은 ProductResponse
     */
    public ProductResponse createProduct(CreateProductRequest request) {
        // 이름 중복 검사를 실행한다.
        if (productRepository.findByName(request.getName()).isPresent()) // 리포지토리 계층에서 상품의 이름을 조회 후 isPresent 한지 확인
            throw new IllegalArgumentException("이미 존재하는 상품입니다."); // 중복 예외처리

        // 엔티티를 생성한다. createProductRequest는 DTO에서 자세히 구현한다.
        Product product = new Product(); // 새 상품 객체 생성
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        // DB에 저장한다
        Product saved = productRepository.save(product);

        // DTO로 변환 후 반환한다.
        return new ProductResponse(
                saved.getId(),
                saved.getName(),
                saved.getPrice(),
                saved.getStock()
        );
    }

    /**
     * 기존 상품에 재고를 추가한다.
     *
     * @param request 상품 ID와 추가할 재고량을 담은 DTO
     * @return 재고가 증가된 상품 정보를 담은 ProductResponse
     */
    public ProductResponse addStock(AddStockRequest request) { // DTO를 받자.
        // 이름으로 상품을 찾는다. 대신 이름과 맞는 상품이 없을 경우 예외 처리 한다. 이때, ()는 람다식으로 매개변수 없는 함수를 의미.
        // 만약 findByName() 결과가 비어 있으면, new Illegal 예외를 만들어 던져라.
        // 상품을 조회한다.
        Product product = productRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("ID와 맞는 상품을 찾을 수 없습니다."));

        // 재고를 증가시킨다.
        product.setStock(product.getStock() + request.getAddStock());

        // DB에 저장한다.
        Product updated = productRepository.save(product);

        // 반환을 DTO로 변환하여 반환한다. 이때 Repository는 Entity만 Save 하므로 기존 코드 수정.
        return new ProductResponse(
                updated.getId(),
                updated.getName(),
                updated.getPrice(),
                updated.getStock()
        );
    }

    /**
     * 하나 이상의 상품을 삭제한다
     *
     * @param request 삭제할 상품 ID 리스트를 담은 DTO
     * @return 삭제 후 남아있는 상품 목록을 ProductResponse 리스트롤 반환한다.
     */
    public ProductsStockResponse deleteProducts(DeleteProductsRequest request) { // DTO를 받도록 수정
        // 또한 여러 개의 입력을 받을 수 있도록 구현해야 한다.
        // 예외 처리는 생략했다.
        // 삭제한다.
        productRepository.deleteAllById(request.getIds()); // DB에서 삭제한다.

        // 삭제 후 전체 목록을 조회한다.
        List<Product> totalStock = productRepository.findAll();

        // 엔티티를 DTO로 매핑한다. DTO로 응답하기로 결정했으니까.
        List<ProductResponse> responses = totalStock.stream()
                .map(p -> new ProductResponse(
                        p.getId(),
                        p.getName(),
                        p.getPrice(),
                        p.getStock()
                ))
                .toList();

        // 만들어진 DTO를 반환한다.
        return new ProductsStockResponse(responses);
    }
}
