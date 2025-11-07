package gdg.hongik.mission;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopService {

//    private final ShopRepository shopRepository;

    public Product searchProduct(String productName) {
        Product product = new Product(); //레포지토리에서 찾아야함..
        return product;
    }

    public Product createProduct(ProductCreateRequest request) {
        Product product = new Product(
                request.getName(),
                request.getPrice(),
                request.getStock()
        );
        return product;
    }

    public Order createOrder(OrderCreateRequest request) {
        Order newOrder = new Order();
        return newOrder; // 완성된 Order 객체 반환
    }

    public void updateProduct(String productName, Integer quantity) {
        return;
    }

    public void deleteProduct(List<String> productName) {
        return;
    }


}
