////*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
package com.lmd.controller;

import com.lmd.service.UserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 *
 * @author Nome
 */
@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;
    
    @GetMapping("/register")
    public String register(@RequestParam Map<String, String> params){
        return "register";
    }
}
