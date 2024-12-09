package com.spring.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class DocumentController {
    
    @GetMapping("/")
    public String swaggerUI() {
        return "redirect:swagger-ui/index.html";
    }
    
}
