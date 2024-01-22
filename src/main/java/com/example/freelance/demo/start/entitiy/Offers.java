package com.example.freelance.demo.start.entitiy;

import com.example.freelance.demo.start.dto.OfferStatus;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "offers")
public class Offers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "JobId")
    private int jobId;
    @Column(name = "user_name")
    private String userName;
    @ManyToOne
    @JoinColumn(name = "user_name", referencedColumnName = "user_name", insertable = false, updatable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "jobId", referencedColumnName = "id", insertable = false, updatable = false)
    private Jobs job;
    @Column(name = "amount")
    private int amount;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OfferStatus offerStatus;
    @OneToOne(mappedBy = "offer", cascade = CascadeType.ALL)
    private com.example.freelance.demo.start.entity.Payment payment;

    public Offers(int id, int jobId, String userName, User user, Jobs job, int amount, OfferStatus offerStatus) {
        this.id = id;
        this.jobId = jobId;
        this.userName = userName;
        this.user = user;
        this.job = job;
        this.amount = amount;
        this.offerStatus = offerStatus;
    }

    public Offers() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Jobs getJob() {
        return job;
    }

    public void setJob(Jobs job) {
        this.job = job;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public OfferStatus getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(OfferStatus offerStatus) {
        this.offerStatus = offerStatus;
    }
}
