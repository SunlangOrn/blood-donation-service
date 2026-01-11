package co.istab.blooddonationservice.presentation;

import co.istab.blooddonationservice.domain.blood.donation.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sync")
@RequiredArgsConstructor
public class SynchronousController {

    private final DonationService donationService;

    @GetMapping("/donations")
    public ResponseEntity<String> syncDonation() {
        donationService.syncAll();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
