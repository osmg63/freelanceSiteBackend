package com.example.freelance.demo.start.service.abstracts;

import com.example.freelance.demo.start.entitiy.Jobs;

import java.util.List;

public interface JobsService {
    List<Jobs> getUserJobs(String userName);
    List<Jobs>  findAll();
    Jobs findById(int id);
    void deleteById(int id);
    Jobs save(Jobs jobs);
    List<Jobs> findAllActiveJobs();
}
