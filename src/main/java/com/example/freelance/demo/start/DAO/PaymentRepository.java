package com.example.freelance.demo.start.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<com.example.freelance.demo.start.entity.Payment,Integer>  {

}
