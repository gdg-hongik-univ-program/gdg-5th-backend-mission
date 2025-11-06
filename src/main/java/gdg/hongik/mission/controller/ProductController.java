package gdg.hongik.mission.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products")
public class ProductController {

    @GetMapping("/{name}")
    public String getProduct(@PathVariable String name){
        name = "apple";
        return name;
    }


}
