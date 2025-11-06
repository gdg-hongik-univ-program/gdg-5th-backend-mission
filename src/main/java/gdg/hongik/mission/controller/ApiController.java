package gdg.hongik.mission.controller;

import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/hello")
    public String hello(){
        return "hello swagger";
    }
}
