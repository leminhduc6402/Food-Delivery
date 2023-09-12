/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lmd.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.lmd.pojo.User;
import com.lmd.repository.UserRepository;
import com.lmd.service.UserService;
import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Nome
 */
@Service("userDetailsService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository UserRepo;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers(Map<String, String> params) {
        return this.UserRepo.getAllUsers(params);
    }

    @Override
    public User getUserById(int id) {
        return this.UserRepo.getUserById(id);
    }

    @Override
    public Boolean addOrUpdateUser(User u) {
        if (u.getFile() != null && !u.getFile().isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(u.getFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            u.setAvatar("https://res.cloudinary.com/dvyk1ajlj/image/upload/v1690620249/xofbyivlqlztrhwlz08u.png");
            User oldUser = this.UserRepo.getUserById(u.getId()); // Lấy thông tin user cũ từ cơ sở dữ liệu
            if (oldUser.getId() != null) {
                u.setAvatar(oldUser.getAvatar()); // Giữ nguyên avatar cũ
            }
        }

        // Đặt giá trị cho trường birth
        u.setBirth(u.getBirth());
        return this.UserRepo.addOrUpdateUser(u);
    }

    @Override
    public Long countUser(Map<String, String> params) {
        return this.UserRepo.countUser(params);
    }

    @Override
    public User getUserByUsername(String uname) {
        return this.UserRepo.getUserByUsername(uname);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = this.UserRepo.getUserByUsername(username);
        if (u == null) {
            throw new UsernameNotFoundException("invalid");
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(String.valueOf(u.getRole())));
        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPassword(), authorities);

    }

    @Override
    public boolean authUser(String username, String password) {
        return this.UserRepo.authUser(username, password);
    }

    @Override
    public boolean deleteUser(int id) {
        return this.UserRepo.deleteUser(id);
    }

    @Override
    public User addUser(Map<String, String> params, MultipartFile avatar) {
        User u = new User();

        u.setName(params.get("name")); // 1
        u.setPhone(params.get("phone")); // 2
        u.setCccd(params.get("cccd")); // 3        
        u.setEmail(params.get("email")); // 4
        String dateString = params.get("birth");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date birthDate = dateFormat.parse(dateString);
            u.setBirth(birthDate); // 5
        } catch (ParseException e) {
            // Xử lý lỗi khi không thể chuyển đổi
        }
        u.setGender((short) Integer.parseInt(params.get("gender"))); // 6
        u.setAddress(params.get("address")); // 7
        u.setRole(params.get("role")); // 8
        if (avatar != null && !avatar.isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            u.setAvatar("https://res.cloudinary.com/dvyk1ajlj/image/upload/v1690620249/xofbyivlqlztrhwlz08u.png");
        }
        u.setUsername(params.get("username")); // 10
        u.setPassword(this.passwordEncoder.encode(params.get("password"))); // 11
        this.UserRepo.addUser(u);
        return u;
    }

    @Override
    public User updateUser(Map<String, String> params, MultipartFile avatar) {
        int id = Integer.parseInt(params.get("id"));
        String name = params.get("name");
        String phone = params.get("phone");
        String cccd = params.get("cccd");
        String email = params.get("email");
        String dateString = params.get("birth");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birth = null;
        try {
            birth = dateFormat.parse(dateString);// 5
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int gender = Integer.parseInt(params.get("gender"));
        String address = params.get("address");
//        String username = params.get("username");
//        String password = params.get("password");

        User u = this.UserRepo.getUserById(id);
        if (u == null)
            return null;
        u.setName(name);
        u.setPhone(phone);
        u.setCccd(cccd);
        u.setEmail(email);
        u.setBirth(birth);
        u.setGender((short) gender);
        u.setAddress(address);
        if (avatar != null && !avatar.isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (u.getAvatar().isEmpty())
                u.setAvatar("https://res.cloudinary.com/dvyk1ajlj/image/upload/v1690620249/xofbyivlqlztrhwlz08u.png");
        }
//        u.setUsername(username);
//        u.setPassword(password);

        this.UserRepo.updateUser(u);
        return u;
    }
}
