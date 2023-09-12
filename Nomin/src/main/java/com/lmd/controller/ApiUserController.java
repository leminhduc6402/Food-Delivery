package com.lmd.controller;

import com.lmd.components.JwtService;
import com.lmd.pojo.User;
import com.lmd.service.UserService;
import java.security.Principal;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Nome
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiUserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/login/")
    public ResponseEntity<String> login(@RequestBody User user) {
        if (this.userService.authUser(user.getUsername(), user.getPassword()) == true) {
            String token = this.jwtService.generateTokenLogin(user.getUsername());
            return new ResponseEntity<>(token, HttpStatus.OK);
        }

        return new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
    }
    @GetMapping(path = "/getCurrentUser", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> getCurrentUser(Principal user){
        User u = this.userService.getUserByUsername(user.getName());
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @PostMapping(path = "/users/",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> addUser(@RequestParam Map<String, String> params, MultipartFile avatar) {      
        User user = this.userService.addUser(params, avatar);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping (path = "/users/{id}",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") int id, @RequestParam Map<String, String> params, MultipartFile avatar) {
        User u = this.userService.getUserById(id);
        if (u == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(this.userService.updateUser(params, avatar), HttpStatus.OK);
    }

    @GetMapping (path = "/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(this.userService.getUserById(id), HttpStatus.CREATED);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(value = "id") int id) {
        this.userService.deleteUser(id);
    }

}
