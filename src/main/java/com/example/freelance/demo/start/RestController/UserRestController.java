package com.example.freelance.demo.start.RestController;

import com.example.freelance.demo.start.dto.AuthRequest;
import com.example.freelance.demo.start.dto.CreateUserRequest;
import com.example.freelance.demo.start.entitiy.Jobs;
import com.example.freelance.demo.start.entitiy.User;
import com.example.freelance.demo.start.service.abstracts.JobsService;
import com.example.freelance.demo.start.service.abstracts.UserService;
import com.example.freelance.demo.start.service.concretes.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserRestController {
    private final JobsService jobsService;
    private final UserService userService;
    private final JwtService jwtService;
    private  final AuthenticationManager authenticationManager;

    public UserRestController(JobsService jobsService, UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.jobsService = jobsService;
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    @GetMapping("/PUBLIC/users")
    public List<User> findAll(){
        return userService.findAll();
    }
    @GetMapping("/users/id/{usersId}")
    public User findById(@PathVariable int usersId){
        User user=userService.findById(usersId);
        return user;
    }
    @GetMapping("/PUBLIC/users/firstName/{firstName}")
    public User getByUserName(@PathVariable String firstName){
        return userService.getUserByFirstName(firstName);
    }
    @GetMapping("/PUBLIC/users/userName/{username}")
    public User getByUserUserName(@PathVariable String username){
        return userService.getUserByUserName(username);
    }

    @PutMapping("/PUBLIC/users")
    public User updateUser(@RequestBody User user){return userService.save(user);}

    @DeleteMapping("/PUBLIC/userId/{userId}")
    public  void deleteUser(@PathVariable int userId){
        User user=userService.findById(userId);
        userService.deleteById(userId);
    }
    @PostMapping

    @GetMapping("/PUBLIC/users/userName/{userName}")
    public List<Jobs> getUserJobs(@PathVariable String userName) {
        List<Jobs> jobs = jobsService.getUserJobs(userName);
        return jobs;
    }
    @GetMapping("/welcome")
    public String welcome(){
        return "hello welcome";
    }
    @PostMapping("/PUBLIC/addNewUser")
    public ResponseEntity<String> addUser(@RequestBody CreateUserRequest request) throws Exception {
        return  userService.createUser(request);
    }
    @PostMapping("/generateToken")
    public String generateToken(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(request.username());
        }
        log.info("invalid username " + request.username());
        throw new UsernameNotFoundException("invalid username {} " + request.username());
    }


}

