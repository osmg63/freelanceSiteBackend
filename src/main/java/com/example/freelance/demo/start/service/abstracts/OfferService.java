package com.example.freelance.demo.start.service.abstracts;

import com.example.freelance.demo.start.entitiy.Offers;

import java.util.List;

public interface OfferService {
        Offers createOffer(Offers offers);
        void acceptOffer(int offerId);
        void rejectOffer(int offerId);
        List<Offers> getOffersForJob(int jobId);
        void makePaymentIfOfferAccepted(int offerId) ;




        }
