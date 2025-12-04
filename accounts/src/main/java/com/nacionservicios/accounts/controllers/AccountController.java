package com.nacionservicios.accounts.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @GetMapping("/hello")
    public String helloWorld(){
        return "Hello World!";
    }
}
