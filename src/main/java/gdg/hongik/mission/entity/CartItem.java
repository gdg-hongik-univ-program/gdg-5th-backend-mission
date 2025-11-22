package gdg.hongik.mission.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 장바구니({@link Cart})에 담긴 개별 상품 항목
 */
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    // 💡 [수정] 상품 이름/가격 대신 Product 엔티티 객체 참조 (N:1 관계)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // 👈 이 객체를 통해 이름과 가격을 얻습니다.

    /** 수량  */
    private int quantity;

    /**
     * 이 상품 항목이 속한 장바구니({@link Cart})
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    // 💡 [추가] CartItem 생성자 (Product 객체를 받음)
    public CartItem(Cart cart, Product product, int quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    /**
     * 이 항목이 속하는 {@link Cart} 객체를 설정
     */
    public void setCart(Cart cart) {
        this.cart = cart;
    }
}