package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringController {
    @RequestMapping("/")
    public String index(){
        return "Hello World!!";
    }
}
