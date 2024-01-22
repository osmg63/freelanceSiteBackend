package com.example.freelance.demo.start.service.abstracts;

import com.example.freelance.demo.start.entitiy.Comment;
import com.example.freelance.demo.start.entitiy.User;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentsByTargetUser(User targetUser);
    Comment addComment(User commenter, User targetUser, String content);
    User getUserCommentsByUsername(String username);
}
