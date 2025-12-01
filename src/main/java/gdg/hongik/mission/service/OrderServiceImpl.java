package gdg.hongik.mission.service;

import gdg.hongik.mission.common.exception.BadRequestException;
import gdg.hongik.mission.common.exception.NotFoundException;
import gdg.hongik.mission.common.message.ErrorMessage;
import gdg.hongik.mission.dto.request.OrderProductRequest;
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
            .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_CART_NONE + userId));

        List<CartItem> cartItems = cart.getItems();

        if (cartItems.isEmpty()) {
            throw new BadRequestException(ErrorMessage.USER_CART_EMPTY);
        }

        // 2. 장바구니 항목 -> 주문 상품 목록으로 변환
        List<OrderProduct> orderProducts = cartItems.stream() //stream 생성 - cartItems의 각 요소를 하나씩 처리 가능
                .map(cartItem -> { //cartItem에 수행할 로직 ->

                    Product product = cartItem.getProduct();

                    if (productRepository.findById(product.getProductId()).isEmpty()) {
                        throw new NotFoundException(
                                ErrorMessage.PRODUCT_NOT_FOUND + product.getProductName() + " (장바구니에 있지만 현재 삭제됨)");
                    }

                    // 각 cartItem에 대해 재고 감소 처리
                    productService.decreaseStock(product.getProductId(), cartItem.getQuantity());

                    // OrderProduct 생성
                    OrderProduct op = OrderProduct.create(new OrderProductRequest(product.getProductName(), cartItem.getQuantity(), product.getPrice()));

                    return op;
                })
                .collect(Collectors.toList()); //OrderProducts 리스트로 변환

        // 3. 주문 객체 생성 및 저장
        Order newOrder = Order.createOrder(orderProducts);
        Order savedOrder = orderRepository.save(newOrder);

        // 4. 장바구니 비우기
        cartRepository.deleteCart(cart);

        return OrderCreateResponse.of(savedOrder);
    }


//

}