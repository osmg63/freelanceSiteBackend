package com.example.freelance.demo.start.DAO;

import com.example.freelance.demo.start.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User getUserByFirstName(String firstName);
    Optional<User> findByUsername(String userName);

    User getUserByUsername(String username);

}
