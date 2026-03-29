package gdg.hongik.mission.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 장바구니({@link Cart})에 담긴 개별 상품 항목
 * 주문이 생성될 때 OrderProduct 객체로 변환되어 거래 정보를 기록
 *
 * @author hyeoniss
 * @since 2025-11-16
 */
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    /** 장바구니에 담긴 상품 이름 */
    private String productName;
    /** 상품 가격 */
    private int price;
    /** 수량  */
    private int quantity;

    /**
     * 이 상품 항목이 속한 장바구니({@link Cart})
     * {@code CartItem}과 {@code Cart}는 N:1 관계
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;


    /**
     * 이 항목이 속하는 {@link Cart} 객체를 설정
     *
     * @param cart 이 항목이 속할 장바구니
     */
    public void setCart(Cart cart) {
        this.cart = cart;
    }
}