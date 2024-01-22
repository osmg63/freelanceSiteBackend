package com.example.freelance.demo.start.RestController;

import com.example.freelance.demo.start.entitiy.Offers;
import com.example.freelance.demo.start.service.abstracts.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OfferRestController {
    private OfferService offerService;
    @Autowired
    public OfferRestController(OfferService offerService){
        this.offerService=offerService;
    }
    @PostMapping("/ISALAN/offers/create")
    public Offers createOffer(@RequestBody Offers offers) {
        offers.setId(0);
        return offerService.createOffer(offers);
    }
    @PostMapping("/ISVEREN/offers/accept/{offerId}")
    public void acceptOffer(@PathVariable int offerId) {
        offerService.acceptOffer(offerId);
    }
    @PostMapping("/ISVEREN/offers/reject/{offerId}")
    public void rejectOffer(@PathVariable int offerId) {
        offerService.rejectOffer(offerId);
    }
    @GetMapping("/PUBLIC/offers/{offersId}")
    public List<Offers> getOffersForJob(@PathVariable int offersId) {
        return offerService.getOffersForJob(offersId);
    }
    @PostMapping("/ISVEREN/makePayment/{offerId}")
    public String makePaymentIfOfferAccepted(@PathVariable int offerId) {
        System.out.println("hello");
        try {
            offerService.makePaymentIfOfferAccepted(offerId);
            return "Payment successful";
        } catch (RuntimeException e) {
            return "Payment failed: " + e.getMessage();
        }
    }

}
