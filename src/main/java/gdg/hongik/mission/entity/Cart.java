package gdg.hongik.mission.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 장바구니 객체
 * 주문 시 필요한 상품 목록을 보관하는 역할
 *
 * @author hyeoniss
 * @since 2025-11-16
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    /**
     * 장바구니 소유자의 ID
     */
    private Long userId;

    /**
     * 장바구니에 담긴 상품 목록
     * {@code Cart}와 {@code CartItem}은 1:N 관계
     * {@code CascadeType.ALL}로 인해 장바구니 삭제 시 모든 {@code CartItem}도 함께 삭제됨
     */
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();


    /**
     * 장바구니에 {@code CartItem}을 추가, {@code CartItem}에도 현재 장바구니({@code this})를 설정하여 양방향 관계를 설정합니다.
     * @param item 추가할 장바구니 상품 항목
     */
    private void addItem(CartItem item) {
        this.items.add(item);
        item.setCart(this);
    }
}