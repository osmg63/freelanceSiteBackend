package com.example.freelance.demo.start.service.concretes;

import com.example.freelance.demo.start.DAO.JobsRepository;
import com.example.freelance.demo.start.DAO.OfferRepository;
import com.example.freelance.demo.start.DAO.UserRepository;
import com.example.freelance.demo.start.entitiy.Jobs;
import com.example.freelance.demo.start.entitiy.Offers;
import com.example.freelance.demo.start.dto.OfferStatus;
import com.example.freelance.demo.start.entitiy.User;
import com.example.freelance.demo.start.service.abstracts.OfferService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final JobsRepository jobsRepository;

    public OfferServiceImpl(OfferRepository offerRepository, UserRepository userRepository, JobsRepository jobsRepository) {
        this.offerRepository = offerRepository;

        this.userRepository = userRepository;
        this.jobsRepository = jobsRepository;
    }

    @Override
    public Offers createOffer(Offers offers) {
        return offerRepository.save(offers);
    }

    @Override
    public void acceptOffer(int offerId) {
        Offers offers = offerRepository.findById(offerId);
        Jobs job = offers.getJob();
        offers.setOfferStatus(OfferStatus.accepted);
        offerRepository.save(offers);
        job.setActive(false);
        jobsRepository.save(job);
    }

    @Override
    @Transactional
    public void makePaymentIfOfferAccepted(int offerId) {
        Offers offer = offerRepository.findById(offerId);

        if (offer == null) {
            throw new RuntimeException("Offer not found");
        }

        if (offer.getOfferStatus() == OfferStatus.accepted) {
            int amount = offer.getAmount();
            User employer = offer.getJob().getUser(); // İşveren
            User freelancer = offer.getUser(); // İşi yapan kişi

            // İşlem öncesi bakiye kontrolü
            if (employer.getWallet() < amount) {
                throw new RuntimeException("Employer does not have sufficient balance for payment");
            }

            // İşverenin cüzdanından kesme
            employer.setWallet(employer.getWallet() - amount);
            userRepository.save(employer);

            // İşi yapan kişinin cüzdanına ekleme
            freelancer.setWallet(freelancer.getWallet() + amount);
            userRepository.save(freelancer);
        } else {
            throw new RuntimeException("Payment can only be made for accepted offers");
        }
    }
    @Override
    public void rejectOffer(int offerId) {
        Offers offers = offerRepository.findById(offerId);
        offers.setOfferStatus(OfferStatus.rejected);
        offerRepository.save(offers);
    }

    @Override
    public List<Offers> getOffersForJob(int jobId) {
        return  offerRepository.findByJobId(jobId);
    }
}
