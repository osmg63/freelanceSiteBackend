package com.example.freelance.demo.start.service.concretes;

import com.example.freelance.demo.start.DAO.CommentRepository;
import com.example.freelance.demo.start.entitiy.Comment;
import com.example.freelance.demo.start.entitiy.User;
import com.example.freelance.demo.start.service.abstracts.CommentService;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    @Override
    public List<Comment> getCommentsByTargetUser(User targetUser) {
        return commentRepository.findByTargetUser(targetUser);
    }

    @Override
    public Comment addComment(User commenter, User targetUser, String content) {
        Comment comment = new Comment();
        comment.setCommenter(commenter);
        comment.setTargetUser(targetUser);
        comment.setContent(content);
        return commentRepository.save(comment);
    }

    @Override
    public User getUserCommentsByUsername(String username) {
        return commentRepository.getUserByUsername(username);
    }


}

