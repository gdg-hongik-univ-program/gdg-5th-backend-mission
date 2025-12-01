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
        private Long cartId;

        private Long userId;
        
        /**
         * 장바구니에 담긴 상품 목록
         * {@code Cart}와 {@code CartItem}은 1:N 관계
         */
        @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<CartItem> items = new ArrayList<>(); //여러 CartItem을 리스트 형태로 보유

        public Cart(Long userId) {
            this.userId = userId;
        } //생성자
    }