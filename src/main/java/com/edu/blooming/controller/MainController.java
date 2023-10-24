package com.edu.blooming.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value="/main")
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
    @GetMapping
    public String main() {
        return "main"; // 
    }
    

    
    
}