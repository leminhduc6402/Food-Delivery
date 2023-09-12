/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.lmd.repository;

import com.lmd.pojo.Restaurant;
import com.lmd.pojo.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Nome
 */
public interface UserRepository {

    List<User> getAllUsers(Map<String, String> params);

    User getUserById(int id);

    boolean addOrUpdateUser(User u);

    Long countUser(Map<String, String> params);

    User getUserByUsername(String uname);

    boolean authUser(String username, String password);
    
    boolean deleteUser(int id);
    
    User addUser(User user);
    
    User updateUser(User user);
}
