package gdg.hongik.mission.service;

import gdg.hongik.mission.dto.response.CartListResponse;
import gdg.hongik.mission.dto.response.OrderCreateResponse;
import gdg.hongik.mission.entity.*;
import gdg.hongik.mission.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductService productService, ProductRepository productRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public OrderCreateResponse createOrderFromCart(Long userId) {

        // 1. 유저 아이디로 장바구니 조회
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));

        List<CartItem> cartItems = cart.getItems();

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty.");
        }

        // 2. 장바구니 항목 -> 주문 상품 목록으로 변환
        List<OrderProduct> orderProducts = cartItems.stream()
                .map(cartItem -> {

                    Product product = cartItem.getProduct();

                    // 재고 감소 처리 (ID로 처리하도록 productService 수정 필요)
                    productService.decreaseStock(product.getId(), cartItem.getQuantity());

                    // OrderProduct 생성 (Product의 최신 이름, 가격 정보 사용)
                    OrderProduct op = OrderProduct.create(
                            new gdg.hongik.mission.dto.request.OrderProductRequest(
                                    product.getName(), cartItem.getQuantity(), product.getPrice())
                    );
                    return op;
                })
                .collect(Collectors.toList());

        // 3. 주문 객체 생성 및 저장
        Order newOrder = Order.createOrder(orderProducts);
        Order savedOrder = orderRepository.save(newOrder);

        // 4. 장바구니 비우기
        cartRepository.deleteCart(cart);

        return OrderCreateResponse.of(savedOrder);
    }


//

}