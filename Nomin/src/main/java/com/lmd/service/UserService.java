/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.lmd.service;

import com.lmd.pojo.Restaurant;
import com.lmd.pojo.User;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Nome
 */
public interface UserService extends UserDetailsService {

    List<User> getAllUsers(Map<String, String> params);

    User getUserById(int id);

    Boolean addOrUpdateUser(User u);

    Long countUser(Map<String, String> params);

    User getUserByUsername(String uname);
    
    boolean authUser(String username, String password);
    
    boolean deleteUser(int id);
    
    User addUser(Map<String, String> params, MultipartFile avatar);
    
    User updateUser(Map<String, String> params, MultipartFile avatar);
}
