package gdg.hongik.mission.service;

import gdg.hongik.mission.dto.response.ProductResponse;
import gdg.hongik.mission.dto.request.ProductCreateRequest;
import gdg.hongik.mission.dto.response.RemainProductsResponse;
import gdg.hongik.mission.entity.Product;
import gdg.hongik.mission.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * {@link ProductService} 인터페이스의 구현체
 *
 * @author hyeoniss
 * @see ProductService
 */
@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    /**
     * {@link ProductRepository} 객체를 주입받는 생성자
     *
     * @param productRepository 상품 데이터 접근 객체
     */
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    /**
     * 상품 이름으로 상품 정보를 검색
     *
     * @param name 검색할 상품 이름
     * @return 검색된 상품 정보를 담은 응답 DTO
     * @throws RuntimeException 상품을 찾을 수 없는 경우
     */
    @Override
    @Transactional
    public ProductResponse searchProduct(String name) {

        Product searchProduct = productRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Product not found: " + name));

        return ProductResponse.of(searchProduct);
    }


    /**
     * 새로운 상품을 생성하여 저장
     * 상품이 이미 존재하면 예외를 발생시킴
     *
     * @param request 상품 생성을 위한 데이터 DTO
     * @return 생성된 상품 정보를 담은 응답 DTO
     * @throws RuntimeException 이미 존재하는 상품일 경우
     */
    @Override
    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request) {

        if (productRepository.findByName(request.getName()).isPresent()) {
            throw new RuntimeException("product already exists: " + request.getName());
        }

        Product newProduct = new Product(request.getName(), request.getPrice(), request.getQuantity());
        Product savedProduct = productRepository.save(newProduct);

        return ProductResponse.of(savedProduct);
    }


    /**
     * 특정 상품의 재고 수량을 추가
     *
     * @param productId 재고를 추가할 상품 이름
     * @param quantity 수정할 수량
     * @return 수정된 상품 정보를 담은 응답 DTO
     * @throws RuntimeException 상품을 찾을 수 없거나 재고가 부족할 경우
     */
    @Override
    @Transactional
    public ProductResponse updateProduct(Long productId, int quantity) { // 💡 name -> productId로 변경

        Product updateProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId)); // 💡 예외 메시지도 ID 기준으로 수정

        updateProduct.increaseStock(quantity);

        return ProductResponse.of(updateProduct);
    }

    /**
     * 지정된 상품들을 삭제
     *
     * @param ids 삭제할 상품 아이디 리스트
     * @return
     */
    @Override
    @Transactional
    public RemainProductsResponse deleteProducts(List<Long> ids) {

        productRepository.deleteAllById(ids);

        List<Product> remainingProducts = productRepository.findAll();

        return RemainProductsResponse.from(remainingProducts);
    }

    /**
     * 상품의 재고를 감소
     * 재고가 부족하면 예외 발생
     *
     * @param id 재고를 감소시킬 상품 이름
     * @param quantity 감소시킬 수량
     * @throws RuntimeException 상품을 찾을 수 없거나 재고가 부족할 경우
     */
    @Transactional
    public void decreaseStock(Long id, int quantity) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));

        product.decreaseStock(quantity);

    }
}