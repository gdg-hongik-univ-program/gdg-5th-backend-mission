package gdg.hongik.mission.service;

import gdg.hongik.mission.entity.Product;
import gdg.hongik.mission.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service // 서비스니까
public class ProductService {
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductByName(String name) {
        return productRepository.findByName(name).orElse(null); // 리포지토리 계층과 상호작용, 이름으로 상품을 찾는다.
    }

    public List<Product> getAll() {
        return productRepository.findAll(); // 전체 상품을 조회한다.
    }

    public Map<String, Object> purchase(Map<String, Object> body) {
        return Map.of(); // 미완성
    }

}
