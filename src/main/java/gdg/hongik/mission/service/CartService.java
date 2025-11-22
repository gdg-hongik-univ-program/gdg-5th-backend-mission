package gdg.hongik.mission.service;

import gdg.hongik.mission.dto.response.CartListResponse;
import gdg.hongik.mission.entity.Cart;
import gdg.hongik.mission.entity.CartItem;
import gdg.hongik.mission.entity.Product;
import gdg.hongik.mission.repository.CartRepository;
import gdg.hongik.mission.repository.OrderRepository;
import gdg.hongik.mission.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public CartService(OrderRepository orderRepository, ProductService productService, ProductRepository productRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

//    @Override
    @Transactional(readOnly = true)
    public Long addItemToCart(Long userId, Long productId, int quantity) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다. ID: " + productId));

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> createNewCart(userId));

        CartItem newItem = new CartItem(cart, product, quantity);

        cart.getItems().add(newItem);
        newItem.setCart(cart);

        cartRepository.saveCart(cart); // 💡 [추가] Cart를 저장하여 CartItem도 함께 저장합니다.

        return newItem.getId(); // 이제 ID가 할당됩니다.
    }

    /**
     * 장바구니가 없는 사용자를 위해 새로운 장바구니를 생성합니다.
     */
    private Cart createNewCart(Long userId) {
        Cart newCart = new Cart(userId); // Cart 엔티티에 Long userId를 받는 생성자가 필요합니다.
        return cartRepository.saveCart(newCart);
    }

//    @Override
    @Transactional(readOnly = true)
    public CartListResponse getCartList(Long userId) {

        // 1. Cart 엔티티 조회 (CartItem 항목까지 즉시 로딩되도록 Fetch Join 가정)
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));

        // 2. CartItems 리스트 추출
        List<CartItem> cartItems = cart.getItems();

        // 3. List<CartItem>을 CartListResponse DTO로 변환
        return CartListResponse.from(cartItems);
    }
}
