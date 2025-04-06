//package com.atriel.springsecurity.model.service;
//
//import com.atriel.springsecurity.model.repository.UserRepository;
//import com.atriel.springsecurity.model.vo.Users;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class UserService {
//    private final UserRepository userRepository;
//
//    @Autowired
//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    public List<Users> findById(String id) {
//        return userRepository.findById(id);
//    }
//}
