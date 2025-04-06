//package com.atriel.springsecurity.controller;
//
//import com.atriel.springsecurity.model.service.UserService;
//import com.atriel.springsecurity.model.vo.Users;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.List;
//
//@Controller
//public class UserController {
//
//    private final UserService userService;
//
//    @Autowired
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping("/login")
//    public String login(String id, Model model) {
//        List<Users> user = userService.findById(id);
//
//        return "/";
//    }
//}
