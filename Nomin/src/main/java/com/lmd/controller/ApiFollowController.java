package com.lmd.controller;

import com.lmd.pojo.Follow;
import com.lmd.pojo.Restaurant;
import com.lmd.pojo.User;
import com.lmd.service.FollowService;
import com.lmd.service.RestaurantService;
import com.lmd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiFollowController {
    @Autowired
    private FollowService followService;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;

    @GetMapping("/follows/{userId}")
    public ResponseEntity<List<Follow>> list(@PathVariable(value = "userId")int userId){
        return new ResponseEntity<>(this.followService.getListFollowByUserId(userId), HttpStatus.OK);
    }

    @PostMapping(path = "/follows/{restaurantId}")
    public ResponseEntity<Follow> addFollow(@PathVariable(value = "restaurantId") int restaurantId, Principal user){
        Restaurant res = this.restaurantService.getRestaurantById(restaurantId);
        User u = this.userService.getUserByUsername(user.getName());

        if (res == null || u == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Follow follow = this.followService.addFollow(restaurantId, user);
        return new ResponseEntity<>(follow, HttpStatus.OK);
    }

    @DeleteMapping("/follows/{restaurantId}")
    public ResponseEntity<Follow> unFollow(@PathVariable(value = "restaurantId")int restaurantId, Principal user){
        Restaurant res = this.restaurantService.getRestaurantById(restaurantId);
        User u = this.userService.getUserByUsername(user.getName());

        if (res == null || u == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        this.followService.unFollow(restaurantId, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/follows/restaurant/{id}")
    public Long countFollower(@PathVariable(value = "id")int id){
        Long count = this.followService.countFollowerByRestaurantId(id);
        return count;
    }

}
