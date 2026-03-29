package gdg.hongik.mission.service;

import gdg.hongik.mission.dto.request.OrderProductRequest;
import gdg.hongik.mission.dto.response.OrderCreateResponse;
import gdg.hongik.mission.entity.Cart;
import gdg.hongik.mission.entity.CartItem;
import gdg.hongik.mission.entity.Order;
import gdg.hongik.mission.entity.OrderProduct;
import gdg.hongik.mission.repository.CartRepository;
import gdg.hongik.mission.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link OrderService} 인터페이스의 구현체
 * 주문 생성, 재고 관리, 장바구니 정리 등.
 *
 * @author hyeoniss
 * @since 2025-11-16
 * @see OrderService
 */
@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final CartRepository cartRepository;

    /**
     * 필요한 객체들을 주입받는 생성자
     *
     * @param orderRepository 주문 데이터 접근 객체
     * @param productService 상품 재고 관리 서비스 객체
     * @param cartRepository 장바구니 데이터 접근 객체
     */
    public OrderServiceImpl(OrderRepository orderRepository, ProductService productService, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.cartRepository = cartRepository;
    }

    /**
     * 특정 사용자의 장바구니에 담긴 상품을 기반으로 주문을 생성
     * 장바구니 조회, 재고 감소 처리, 주문 객체 생성 및 저장, 장바구니 삭제가 각각 하나의 트랜잭션으로 처리됨
     *
     * @param userId 주문을 생성할 사용자의 ID
     * @return 생성된 주문의 결과를 담은 응답 DTO
     * @throws RuntimeException 장바구니를 찾을 수 없거나 장바구니가 비어 있을 경우
     */
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

                    // 재고 감소 처리
                    productService.decreaseStock(cartItem.getProductName(), cartItem.getQuantity());

                    // OrderProduct 생성 (CartItem 정보를 OrderProductRequest DTO로 변환하여 사용)
                    OrderProduct op = OrderProduct.create(
                            new gdg.hongik.mission.dto.request.OrderProductRequest(
                                    cartItem.getProductName(), cartItem.getQuantity(), cartItem.getPrice())
                    );
                    return op;
                })
                .collect(Collectors.toList());

        // 3. 주문 객체 생성 및 저장
        Order newOrder = Order.createOrder(orderProducts);
        Order savedOrder = orderRepository.save(newOrder);

        // 4. 장바구니 비우기
        cartRepository.delete(cart);

        return OrderCreateResponse.of(savedOrder);
    }
}