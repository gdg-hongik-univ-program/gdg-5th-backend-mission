package gdg.hongik.mission.service;

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

    public Product create(Product p) {
        if (productRepository.findByName(p.getName()).isPresent()) // 리포지토리 계층에서 상품의 이름을 조회 후 isPresent 한지 확인
            throw new IllegalArgumentException("이미 존재하는 상품입니다."); // 중복 예외처리
        return productRepository.save(p); // 그렇지 않다면 상품을 저장한다.
    }

    public Product addStock(Long id, int addStock) { // id를 사용하자.
        // 이름으로 상품을 찾는다. 대신 이름과 맞는 상품이 없을 경우 예외 처리 한다. 이때, ()는 람다식으로 매개변수 없는 함수를 의미.
        // 만약 findByName() 결과가 비어 있으면, new Illegal 예외를 만들어 던져라.
        Product product = productRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("ID와 맞는 상품을 찾을 수 없습니다."));
        product.setStock(product.getStock() + addStock);
        return productRepository.save(product);
    }

    // 한 개만 지우는 설계는 배제했다.
    public List<Product> deleteProducts(List<Long> ids) { //삭제 후 남은거 모두 보이기 위해 List<Product>
        // 또한 여러 개의 입력을 받을 수 있도록 구현해야 한다.
        // 예외 처리는 생략했다.
        productRepository.deleteAllById(ids); // DB에서 삭제한다.
        return productRepository.findAll(); // 삭제 후 남은 전체 상품 목록을 반환하여 컨트롤러에게 넘겨준다.
    }
}
