package co.istab.blooddonationservice.presentation.blood.donation;

import co.istab.blooddonationservice.domain.blood.donation.entity.Donation;
import co.istab.blooddonationservice.domain.blood.donation.service.DonationService;
import co.istab.blooddonationservice.presentation.blood.donation.mapper.DonationControllerMapper;
import co.istab.blooddonationservice.presentation.blood.donation.model.request.CreateDonationRequest;
import co.istab.blooddonationservice.presentation.blood.donation.model.request.UpdateDonationRequest;
import co.istab.blooddonationservice.presentation.blood.donation.model.response.DonationResponse;
import co.istab.blooddonationservice.presentation.blood.donation.model.response.DonationDetailResponse;
import co.istab.blooddonationservice.share.entity.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static co.istab.blooddonationservice.share.api.ControllerHandler.*;

@RestController
@RequestMapping("/api/v1/donations")
@RequiredArgsConstructor
public class DonationController {

    private final DonationService donationService;
    private final DonationControllerMapper mapper;

    @GetMapping()
    public ResponseEntity<HttpBodyResponse<List<DonationDetailResponse>>> list(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String typeBlood,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String location
            )
    {

        Paging<Donation> entityPaging =
                donationService.list(
                        new Metadata(),
                        PaginationQuery.of(page, size, keyword, typeBlood, status, location, null));

        return responsePaging(
                entityPaging.getItems().stream().map(mapper::toDonationDetailResponse).toList(),
                HttpBodyPagingResponse.of(
                        entityPaging.getPage(),
                        entityPaging.getSize(),
                        entityPaging.getTotal(),
                        entityPaging.getTotalPages()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpBodyResponse<DonationDetailResponse>> donation(@PathVariable Integer id){
        return responseSucceed(mapper.toDonationDetailResponse(donationService.view(new Metadata(), id)));
    }


    @PostMapping("/create")
    public ResponseEntity<HttpBodyResponse<DonationResponse>> createDonation(
            @Valid@RequestBody CreateDonationRequest createDonationRequest, Metadata metadata){
        Donation domain = mapper.from(createDonationRequest);
        return responseCreated(mapper.from(donationService.create(metadata ,domain)));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HttpBodyResponse<DonationResponse>> updateDonation(
            @PathVariable Integer id ,
            @Valid @RequestBody UpdateDonationRequest updateDonationRequest,
            Metadata metadata
    ){
        Donation  domain = mapper.from(updateDonationRequest);
        return responseCreated(mapper.from(donationService.update(metadata ,id ,domain)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        donationService.delete(new Metadata() ,id);
        return responseDeleted();
    }

}
