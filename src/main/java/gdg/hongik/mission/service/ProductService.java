package gdg.hongik.mission.service;


import gdg.hongik.mission.dto.Product;
import gdg.hongik.mission.dto.response.ProductGetResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
public class ProductService {

    HashMap<Long, Product> repository = new HashMap<>();
    private Long idSequence = 1L;

    @Transactional
    public void getProduct (String productName){

        ProductGetResponse result = ProductGetResponse.builder()
                .id(entry.getKey()) // 여기서 id 주입
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }



}
