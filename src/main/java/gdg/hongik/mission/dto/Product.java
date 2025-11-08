package gdg.hongik.mission.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Product {

    private String name;

    private Long price;

    private Long stock;

    @Builder
    public Product(String name, Long price, Long stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }


    public void updateProduct(Long stock){
        if(stock != null) {
            this.stock = stock;
        }
    }
}
