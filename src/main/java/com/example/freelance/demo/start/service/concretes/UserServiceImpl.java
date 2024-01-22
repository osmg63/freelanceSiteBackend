package com.example.freelance.demo.start.service.concretes;

import com.example.freelance.demo.start.DAO.UserRepository;
import com.example.freelance.demo.start.dto.CreateUserRequest;
import com.example.freelance.demo.start.entitiy.Jobs;
import com.example.freelance.demo.start.entitiy.User;
import com.example.freelance.demo.start.mernis.FNKKPSPublicSoap;
import com.example.freelance.demo.start.service.abstracts.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        Optional<User> result= userRepository.findById(id);
        User user=null;
        user=result.get();
        return user;
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserByFirstName(String Name) {
        return userRepository.getUserByFirstName(Name);
    }

    @Override
    public User getUserByUserName(String username) {
        return userRepository.getUserByUsername(username);
    }

    public Optional<User> getByUserName(String userName){
        return userRepository.findByUsername(userName);
    }
    public ResponseEntity<String> createUser(CreateUserRequest request) throws Exception {
        FNKKPSPublicSoap client = new FNKKPSPublicSoap();

        try {
            long identityNumber = Long.parseLong(request.identityNumber());
            boolean isRealUser = client.TCKimlikNoDogrula(identityNumber, request.firstName(), request.lastName(), request.age());

            if (isRealUser) {
                User newUser = User.builder()
                        .firstName(request.firstName())
                        .lastName(request.lastName())
                        .wallet(request.wallet())
                        .email(request.email())
                        .identityNumber(request.identityNumber())
                        .username(request.username())
                        .password(passwordEncoder.encode(request.password()))
                        .authorities(request.authorities())
                        .accountNonExpired(true)
                        .credentialsNonExpired(true)
                        .isEnabled(true)
                        .accountNonLocked(true)
                        .build();
                System.out.println("kullanıcı eklendi" + newUser.getAuthorities());
                userRepository.save(newUser);
                return ResponseEntity.ok("başarılı");
            } else {
                return ResponseEntity.badRequest().body("böyle biri yok");
            }
        } catch (NumberFormatException e) {
            // Hata durumunda ilgili durumu ele alın
            e.printStackTrace(); // Hata mesajını görüntüle
            return ResponseEntity.badRequest().body("Kimlik numarası sayıya dönüştürülemedi.");
        }
    }

    public void addPhotoToUser(String  username, byte[] photoData) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPhoto(photoData);
            userRepository.save(user);
            user.setPhoto(photoData);
            userRepository.save(user);
        } else {
            System.out.println("addPhotoError,");
        }

    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user=userRepository.findByUsername(username);
        return user.orElseThrow(EntityNotFoundException::new);
    }

}
