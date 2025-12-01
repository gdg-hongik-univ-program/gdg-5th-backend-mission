package gdg.hongik.mission.service;

import gdg.hongik.mission.common.exception.NotFoundException;
import gdg.hongik.mission.common.message.ErrorMessage;
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

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> createNewCart(userId));

        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new NotFoundException(ErrorMessage.PRODUCT_NOT_FOUND + productId));

        CartItem newItem = new CartItem(cart, product, quantity);

        cart.getItems().add(newItem);
        newItem.setCart(cart);

        cartRepository.saveCart(cart);

        return newItem.getId();
    }

    /**
     * 장바구니가 없는 사용자를 위해 새로운 장바구니를 생성
     */
    private Cart createNewCart(Long userId) {
        Cart newCart = new Cart(userId); 
        return cartRepository.saveCart(newCart);
    }

}
