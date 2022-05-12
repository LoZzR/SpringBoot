package com.spring.boot.controllers;

import com.spring.boot.services.PersonAudit;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HomeController {

    private PersonAudit audit;

    public HomeController(PersonAudit audit) {
        this.audit = audit;
    }

    @RequestMapping(value = {"/","/home"}, method = RequestMethod.GET)
    public String home() {
        StringBuilder sb = new StringBuilder();
        audit.getAuditMessages().forEach((k,v) ->
                sb.append(k).append(": ").append(v).append("\n"));
        return sb.toString();
    }
}
