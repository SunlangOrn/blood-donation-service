package co.istab.blooddonationservice.presentation.blood.donation_action;

import co.istab.blooddonationservice.domain.blood.donation_action.entity.DonationAction;
import co.istab.blooddonationservice.domain.blood.donation_action.service.DonationActionService;
import co.istab.blooddonationservice.presentation.blood.donation_action.mapper.DonationActionControllerMapper;
import co.istab.blooddonationservice.presentation.blood.donation_action.model.response.DonationActionResponse;
import co.istab.blooddonationservice.share.entity.HttpBodyResponse;
import co.istab.blooddonationservice.share.entity.Metadata;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static co.istab.blooddonationservice.share.api.ControllerHandler.responseCreated;

@RestController
@RequestMapping("/api/v1/donation-action")
@RequiredArgsConstructor
public class DonationActionController {

    private final DonationActionService donationActionService;
    private final DonationActionControllerMapper mapper;

    @GetMapping
    public ResponseEntity<HttpBodyResponse<DonationActionResponse>> actionId(
            Metadata metadata,
            @RequestParam Integer actionId){

        DonationAction action = donationActionService.getActionId(metadata, actionId);
        return responseCreated(mapper.from(action));
    }

    @PostMapping("/donations/donate/{donationId}")
    public ResponseEntity<HttpBodyResponse<DonationActionResponse>> donate(
            Metadata metadata,
            @PathVariable Integer donationId){
        DonationAction action  = donationActionService.donate(metadata,donationId);
        DonationActionResponse actionResponse = mapper.from(action);
        return responseCreated(actionResponse);
    }

    @PostMapping("/cancel/{actionId}")
    public ResponseEntity<HttpBodyResponse<DonationActionResponse>> cancel(
            @PathVariable Integer actionId
    ){
        DonationAction action  = donationActionService.cancel(new Metadata(),actionId);
        DonationActionResponse actionResponse = mapper.from(action);
        return responseCreated(actionResponse);
    }

    @PostMapping("/accept/{actionId}")
    public ResponseEntity<HttpBodyResponse<DonationActionResponse>> accept(
            @PathVariable Integer actionId
    ){
        DonationAction action  = donationActionService.accept(new Metadata(), actionId);
        DonationActionResponse actionResponse = mapper.from(action);
        return responseCreated(actionResponse);
    }

    @PostMapping("/reject/{actionId}")
    public ResponseEntity<HttpBodyResponse<DonationActionResponse>> reject(
            @PathVariable Integer actionId
    ){
        DonationAction action  = donationActionService.reject(new Metadata(), actionId);
        DonationActionResponse actionResponse = mapper.from(action);
        return responseCreated(actionResponse);
    }
}

