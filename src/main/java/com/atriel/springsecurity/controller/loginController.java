package com.atriel.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class loginController {
    @GetMapping("/loginSuccess")
    @ResponseBody
    public String loginSuccess() {
        return "<h1>Your login is Success!!</h1>";
    }
}
