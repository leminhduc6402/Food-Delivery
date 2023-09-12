/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lmd.service.impl;

import com.lmd.pojo.CommentsFood;
import com.lmd.repository.CommentFoodRepository;
import com.lmd.service.CommentFoodService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nome
 */
@Service
public class CommentFoodServiceImpl implements CommentFoodService{
    @Autowired
    private CommentFoodRepository commentFoodRepo;
    @Override
    public List<CommentsFood> getAllCommentByFoodId(int id) {
        return this.commentFoodRepo.getAllCommentByFoodId(id);
    }

    @Override
    public CommentsFood addComment(CommentsFood cmt) {
        return this.commentFoodRepo.addComment(cmt);
    }

}
