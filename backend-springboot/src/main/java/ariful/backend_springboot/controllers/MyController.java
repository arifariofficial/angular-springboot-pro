package ariful.backend_springboot.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MyController {
    @RequestMapping("/hello")
    public String hello(){
        return "Hello, World!";
    }
}
