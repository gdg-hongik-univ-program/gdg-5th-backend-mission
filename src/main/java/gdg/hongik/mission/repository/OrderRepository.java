package gdg.hongik.mission.repository;

import gdg.hongik.mission.entity.Cart;
import gdg.hongik.mission.entity.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 주문 객체({@link Order})에 대한 Repository
 *
 * @author [작성자 이름]
 */
@Repository
public class OrderRepository {

    @PersistenceContext
    private EntityManager em;

    /**
     * 새로운 주문 엔티티를 데이터베이스에 저장
     * JPA의 {@code persist} 메서드를 사용
     *
     * @param order 저장할 주문 엔티티 객체
     * @return 저장된 주문 엔티티 객체
     */
    public Order save(Order order){
        em.persist(order);
        return order;
    }

}