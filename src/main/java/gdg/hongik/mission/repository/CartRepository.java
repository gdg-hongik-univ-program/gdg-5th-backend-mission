package gdg.hongik.mission.repository;

import gdg.hongik.mission.entity.Cart;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 장바구니 객체({@link Cart})에 대한 Repository
 * 장바구니 조회 및 삭제에 사용
 */
@Repository
@NoArgsConstructor
public class CartRepository {

    @PersistenceContext
    private EntityManager em;

    /**
     * 사용자 ID로 장바구니를 조회
     * @param userId 조회할 사용자 ID
     * @return 장바구니 객체를 포함하는 Optional
     */
    public Optional<Cart> findByUserId(Long userId) {
        List<Cart> result = em.createQuery(
                        "SELECT t FROM Cart t WHERE t.userId = :userId", Cart.class)
                .setParameter("userId", userId)
                .getResultList();

        return result.isEmpty()
                ? Optional.<Cart>empty()
                : Optional.of(result.get(0));
    }


    /**
     * 주문 완료 후 장바구니를 삭제
     * @param cart 삭제할 장바구니 객체
     */
    public void deleteCart(Cart cart) {
        em.remove(cart);
    }

    public Cart saveCart(Cart newCart) {
        em.persist(newCart);
        return newCart;
    }

}