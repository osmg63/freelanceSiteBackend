package com.example.freelance.demo.start.RestController;

import com.example.freelance.demo.start.entitiy.Jobs;
import com.example.freelance.demo.start.entitiy.User;
import com.example.freelance.demo.start.service.abstracts.JobsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class JobsController {
    private JobsService jobsService;
    @Autowired
    public JobsController(JobsService jobsService){
        this.jobsService=jobsService;
    }
    @GetMapping("/PUBLIC/jobs/all")
    public List<Jobs> findAll(){
        return jobsService.findAll();
    }
    @GetMapping("/PUBLIC/jobs/active")
    public List<Jobs> findAllActive(){
        return jobsService.findAllActiveJobs();
    }
    @GetMapping("/PUBLIC/jobs/id/{jobsId}")
    public Jobs findById(@PathVariable int jobsId){
        Jobs jobs=jobsService.findById(jobsId);
        return jobs;
    }
    @PostMapping("/ISVEREN/jobs")
    public Jobs addJobs(@RequestBody Jobs jobs){
        jobs.setId(0);
        return jobsService.save(jobs);
    }
    @PostMapping("/ISVEREN/createJob")
    public ResponseEntity<String> createJob(@RequestBody Map<String, Object> request) {
        try {
            String title = (String) request.get("title");
            String description = (String) request.get("description");
            int active = (int) request.get("active");
            int price = (int) request.get("price");
            String deadline = (String) request.get("deadline");
            String userName = (String) request.get("userName");
            String photoBase64 = (String) request.get("photo");

            byte[] photoData = Base64.getDecoder().decode(photoBase64.split(",")[1]);

            // Diğer işlemler...

            // Örnek bir işlem: İlanı veritabanına kaydetme
            Jobs job = new Jobs();
            job.setTitle(title);
            job.setDescription(description);
            job.setPrice(price);
            job.setUserName(userName);
            job.setPhoto(photoData);

            jobsService.save(job); // Bu metodu kendi servis sınıfınıza göre ayarlamalısınız

            return ResponseEntity.ok("İlan başarıyla oluşturuldu");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("İlan oluşturma hatası");
        }
    }

    @PutMapping("/ISVEREN/jobs")
    public Jobs updateJobs(@RequestBody Jobs jobs){return jobsService.save(jobs);}
    @DeleteMapping("/ISVEREN/jobs/{jobsId}")
    public  void deleteUser(@PathVariable int jobsId){
        Jobs jobs=jobsService.findById(jobsId);
        jobsService.deleteById(jobsId);
    }
    @GetMapping("/PUBLIC/jobs/user/{userName}")
    public List<Jobs> findByUserName(@PathVariable String userName){
        return  jobsService.getUserJobs(userName);
    }
}
