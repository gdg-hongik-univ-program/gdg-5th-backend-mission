package gdg.hongik.mission.repository;

import gdg.hongik.mission.entity.Order;
import gdg.hongik.mission.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 상품 객체({@link Product})에 대한 Repository

 * @author hyeoniss
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

//
//    @PersistenceContext
//    private EntityManager em;

    /**
     * 상품 이름으로 상품을 조회
     *
     * @param name 조회할 상품 이름
     * @return 존재할 경우 {@link Product}를 포함하는 {@code Optional}, 없을 경우 빈 {@code Optional}
     */
    Optional<Product> findByName(String name);
    Optional<Product> findById(Long id);

}