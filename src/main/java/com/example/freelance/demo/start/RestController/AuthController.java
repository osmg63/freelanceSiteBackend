package com.example.freelance.demo.start.RestController;


import com.example.freelance.demo.start.dto.AuthRequest;
import com.example.freelance.demo.start.dto.AuthResponse;
import com.example.freelance.demo.start.service.abstracts.UserService;
import com.example.freelance.demo.start.service.concretes.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UserService userService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/PUBLIC/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        // Kullanıcı adı ve şifre ile authentication token oluştur
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password())
        );

        // Authentication başarılıysa, JWT token oluştur
        if (authentication.isAuthenticated()) {
            String username = authRequest.username();
            UserDetails userDetails = userService.loadUserByUsername(username);
            String token = jwtService.generateToken(username);

            // AuthResponse nesnesini oluştur ve geri döndür
            AuthResponse authResponse = new AuthResponse();
            authResponse.setAccessToken("Bearer " + token);
            authResponse.setUserId(userDetails.getUsername());
            // refresh token, kullanıcıya gönderilmiş olabilir veya kullanıcı bazında bir yönetim ekranı oluşturularak kullanıcıya sunulabilir
            authResponse.setRefreshToken("RefreshToken");

            return authResponse;
        } else {
            // Authentication başarısız ise, hata mesajı döndürülebilir
            throw new RuntimeException("Authentication failed");
        }

    }
}
