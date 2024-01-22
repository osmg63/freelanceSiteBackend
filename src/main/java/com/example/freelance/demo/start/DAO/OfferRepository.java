package com.example.freelance.demo.start.DAO;

import com.example.freelance.demo.start.entitiy.Offers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offers,Integer> {
    Offers findById(int offerId);

    List<Offers> findByJobId(int jobId);
}
