package gdg.hongik.mission.controller;



import gdg.hongik.mission.dto.Product;
import gdg.hongik.mission.dto.request.ProductCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("products")
public class ProductController {

    @GetMapping("/{name}")
    public ResponseEntity<Product> getProduct(@PathVariable String name){
        Product product = new Product();

        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody ProductCreateRequest request){
        ProductCreateRequest result = new ProductCreateRequest(
                request.
        )
        return ResponseEntity.created(URI.create("products")).build();
    }


}
