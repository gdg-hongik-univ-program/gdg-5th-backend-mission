package gdg.hongik.mission.dto;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long price;

    private Long stock;

    @Builder
    public Product(String name, Long price, Long stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    @Builder
    public void updateProduct(Long stock){
        if(stock != null) {
            this.stock = stock;
        }
    }
}
