package com.example.freelance.demo.start.DAO;

import com.example.freelance.demo.start.entitiy.Jobs;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobsRepository extends JpaRepository<Jobs, Integer> {
    @Query("SELECT j FROM Jobs j WHERE j.active = true")
    List<Jobs> findAllActiveJobs();

}
