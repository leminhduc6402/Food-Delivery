/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.lmd.service;

import com.lmd.pojo.CommentsFood;
import java.util.List;

/**
 *
 * @author Nome
 */
public interface CommentFoodService {
    List<CommentsFood> getAllCommentByFoodId(int id);
    CommentsFood addComment(CommentsFood cmt);
}
