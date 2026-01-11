package co.istab.blooddonationservice.infrastructure.database.elasticsearch;

import co.istab.blooddonationservice.domain.blood.donation.entity.Donation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class DonationSyncAspect {

    private final DonationElasticsearchService donationElasticsearchService;

    @AfterReturning(
            pointcut = "@annotation(co.istab.blooddonationservice.domain.blood.donation.handler.DonationElasticsearchSync)", returning = "result"
    )
    public void handle(Donation result) {

        if(Objects.nonNull(result.getDeletedAt()))
            donationElasticsearchService.delete(result.getId());
        else
            donationElasticsearchService.save(result.getId());
    }
}