package com.spring.boot.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class BeansController {

    @Autowired
    ApplicationContext ctx;

    @GetMapping("/")
    public String index() {
        StringBuilder sb = new StringBuilder("<html><body>");
        sb.append("Hello there dear developer, here are the beans you were looking for: </br>");
        //method that returns all the bean names in the context of the application
        Arrays.stream(ctx.getBeanDefinitionNames()).sorted().forEach(
                beanName -> sb.append("</br>").append(beanName)
        );
        sb.append("</body></htm>");
        return sb.toString();
    }
}
