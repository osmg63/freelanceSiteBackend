package com.example.freelance.demo.start.RestController;

import com.example.freelance.demo.start.entitiy.Comment;
import com.example.freelance.demo.start.entitiy.User;
import com.example.freelance.demo.start.service.abstracts.CommentService;
import com.example.freelance.demo.start.service.abstracts.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class CommentRestController {

    private final UserService userService;
    private final CommentService commentService;

    public CommentRestController(UserService userService, CommentService commentService) {
        this.userService = userService;
        this.commentService = commentService;
    }

    @PostMapping("/PUBLIC/addCOMMENT")
    public Comment addComment(@RequestParam String commenterUsername,
                              @RequestParam String targetUsername,
                              @RequestParam String content) {
        // Bu kısımları, gerçek kullanıcı bilgilerini alacak şekilde uygun bir şekilde düzenleyin
        User commenter = userService.getUserByUserName(commenterUsername);
        User targetUser = userService.getUserByUserName(targetUsername);
        return commentService.addComment(commenter, targetUser, content);
    }

    @GetMapping("/PUBLIC/comment/{username}")
    public List<Comment> getCommentsByTargetUser(@PathVariable String username) {
        // Kullanıcı adına göre yorumları getir
        User targetUser = userService.getUserByUsername(username);
        return commentService.getCommentsByTargetUser(targetUser);
    }


}
