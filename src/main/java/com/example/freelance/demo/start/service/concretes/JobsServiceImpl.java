package com.example.freelance.demo.start.service.concretes;

import com.example.freelance.demo.start.DAO.JobsRepository;
import com.example.freelance.demo.start.entitiy.Jobs;
import com.example.freelance.demo.start.entitiy.User;
import com.example.freelance.demo.start.service.abstracts.JobsService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobsServiceImpl implements JobsService {
    private JobsRepository jobsRepository;
    private EntityManager entityManager;
    @Autowired
    public JobsServiceImpl(JobsRepository jobsRepository,EntityManager entityManager){
        this.jobsRepository=jobsRepository;
        this.entityManager=entityManager;


    }
    @Override
    public List<Jobs> findAll() {
        return jobsRepository.findAll();
    }

    @Override
    public Jobs findById(int id) {
        Optional<Jobs> result=jobsRepository.findById(id);
        return result.get();
    }

    @Override
    public void deleteById(int id) {
            jobsRepository.deleteById(id);
    }

    @Override
    public Jobs save(Jobs jobs) {
        return jobsRepository.save(jobs);
    }

    @Override
    public List<Jobs> findAllActiveJobs() {
        return jobsRepository.findAllActiveJobs();
    }

    public List<Jobs> getUserJobs(String userName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("GetProjectsByUserName");
        query.registerStoredProcedureParameter("inputUserName", String.class, ParameterMode.IN);
        query.setParameter("inputUserName", userName);
        query.execute();
        List<Jobs> jobs = query.getResultList();
        return jobs;
    }
}
