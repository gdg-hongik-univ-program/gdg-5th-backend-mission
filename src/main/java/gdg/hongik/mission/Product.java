//그냥 상품
package gdg.hongik.mission;

    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
    import lombok.Getter;
    import lombok.NoArgsConstructor;

    @Entity
    @Getter
    @NoArgsConstructor
    public class Product {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
//        private Long productId;
        private String productName; //물건 이름
        private Long price; //물건 가격
        private Long stock; //물건 재고


        public Product(String name, Long price, Long stock) {
            this.productName = name;
            this.price = price;
            this.stock = stock;
        }

    }
