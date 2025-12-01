//그냥 상품
package gdg.hongik.mission.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 정보를 나타내는 객체
 * 재고 수량, 가격 등의 정보 포함
 *
 * @author hyeoniss
 */
@Entity
@Getter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    /** 상품 이름  */
    @Column(nullable = false, unique = true)
    private String productName;

    /** 상품 단가 */
    @Column(nullable = false)
    private int price;

    /** 재고 수량 */
    @Column(nullable = false)
    private int quantity;

    /**
     * 상품 객체 생성자
     *
     * @param name 상품 이름
     * @param price 상품 단가
     * @param quantity 초기 재고 수량
     */
    public Product(String name, int price, int quantity) {
        this.productName = name;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * 재고 수량을 감소
     *
     * @param quantity 감소시킬 수량
     * @throws RuntimeException 요청 수량이 현재 재고보다 많을 경우
     */
    public void decreaseStock(int quantity) {
        if (quantity > this.quantity) { throw new RuntimeException("quantity must be less than or equal to the stock quantity");}
        this.quantity -= quantity;
    }

    /**
     * 재고 수량을 증가
     *
     * @param quantity 증가시킬 수량
     * @throws RuntimeException 증가 시킬 수량이 0보다 작을 경우
     */
    public void increaseStock(int quantity) {
        if (quantity < 0) { throw new RuntimeException("quantity cannot be less than 0");}
        this.quantity += quantity;
    }

}