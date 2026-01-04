package co.istab.blooddonationservice.presentation.blood.report;

import co.istab.blooddonationservice.domain.blood.donation.entity.Donation;
import co.istab.blooddonationservice.domain.blood.donation.service.DonationService;
import co.istab.blooddonationservice.domain.blood.donation_action.entity.DonationAction;
import co.istab.blooddonationservice.domain.blood.donation_action.service.DonationActionService;
import co.istab.blooddonationservice.presentation.blood.donation.mapper.DonationControllerMapper;
import co.istab.blooddonationservice.presentation.blood.donation_action.mapper.DonationActionControllerMapper;
import co.istab.blooddonationservice.share.entity.HttpBodyResponse;
import co.istab.blooddonationservice.share.entity.Metadata;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static co.istab.blooddonationservice.share.api.ControllerHandler.responseCreated;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final DonationService donationService;
    private final DonationControllerMapper donationMapper;
    private final DonationActionService actionService;
    private final DonationActionControllerMapper actionMapper;


    @GetMapping("/my-requests")
    public ResponseEntity<HttpBodyResponse<List<DonationReportResponse>>> getMyRequest(
            Metadata metadata
    ){

        List<Donation> domain = donationService.myRequests(metadata);
        return responseCreated(domain.stream().map(donationMapper::fromList).toList());
    }

    @GetMapping("/my-donation")
    public ResponseEntity<HttpBodyResponse<List<MyDonationReportResponse>>> getMyDonation(
            Metadata metadata
    )
    {
        List<DonationAction> domain = actionService.getUserId(metadata);
        return  responseCreated(domain.stream().map(actionMapper::fromList).toList());
    }


}
