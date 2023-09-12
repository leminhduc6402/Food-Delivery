/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lmd.controller;

import com.lmd.pojo.CommentsFood;
import com.lmd.service.CommentFoodService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Nome
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiCommentController {
    @Autowired
    private CommentFoodService commentFoodService;
    
    @RequestMapping("/comments/{id}")
    public ResponseEntity<List<CommentsFood>> list(@PathVariable(value = "id") int id){
        return new ResponseEntity<>(this.commentFoodService.getAllCommentByFoodId(id), HttpStatus.OK);
    }
    
}
