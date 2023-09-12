/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lmd.controller;

import com.lmd.pojo.CommentsFood;
import com.lmd.pojo.Food;
import com.lmd.pojo.User;
import com.lmd.service.CommentFoodService;
import com.lmd.service.FollowService;
import com.lmd.service.FoodService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Nome
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiFoodController {
    @Autowired
    private CommentFoodService commentFoodService;
    @Autowired
    private FoodService foodService;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private FollowService followService;


    @RequestMapping("/foods/{id}")
    public ResponseEntity<List<Food>> list(@PathVariable(value = "id") int id, @RequestParam Map<String, String> params) {
        params.put("resId", String.valueOf(id));
        return new ResponseEntity<>(this.foodService.getAllFoods(params), HttpStatus.OK);
    }

    @PostMapping(path = "/foods/",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Food> addFood(@RequestParam Map<String, String> params, MultipartFile image){
        Food food = this.foodService.addFood(params, image);
        List<User> list = this.followService.getListFollowByRestaurantId(food.getRestaurantId().getId());
        if (list.size() > 0){
            list.forEach(
                    user -> {
                        MimeMessage message = javaMailSender.createMimeMessage();
                        try {
                            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                            helper.setTo(user.getEmail());
                            helper.setSubject("Thông báo từ " + food.getRestaurantId().getName());
                            helper.setText("Món ăn mới: " + food.getName(), true);
                            javaMailSender.send(message);
                        } catch (MessagingException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
        }
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @RequestMapping("/foods/food/{id}")
    public ResponseEntity<Food> getFoodById(@PathVariable(value = "id") int id) {
        return new ResponseEntity<Food>(this.foodService.getFoodById(id), HttpStatus.OK);
    }

    @GetMapping("/foods/food/{id}/getAverageRateForFood")
    public Double getAverageRateForFood(@PathVariable(value = "id") int id) {
        return this.foodService.getAverageRateForFood(id);
    }
    
    @PostMapping(path = "/foods/{id}/updateFood",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Food> updateFood(@PathVariable(value = "id") int id, @RequestParam Map<String, String> params, MultipartFile image){

        Food f = foodService.getFoodById(id);
        if (f == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.foodService.updateFood(params, image);
        return new ResponseEntity<>(HttpStatus.OK); 
    }

    @GetMapping("/foods/{id}/comments")
    public ResponseEntity<List<CommentsFood>> listComment(@PathVariable(value = "id")int id) {
        return new ResponseEntity<>(this.commentFoodService.getAllCommentByFoodId(id), HttpStatus.OK);
    }

    @PostMapping(path = "/comments/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentsFood> addComment(@RequestBody CommentsFood commentsFood){
        CommentsFood cmt = this.commentFoodService.addComment(commentsFood);
        return new ResponseEntity<>(cmt, HttpStatus.CREATED);
    }
}