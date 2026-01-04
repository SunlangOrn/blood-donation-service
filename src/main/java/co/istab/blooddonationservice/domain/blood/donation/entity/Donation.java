package co.istab.blooddonationservice.domain.blood.donation.entity;

import co.istab.blooddonationservice.domain.blood.donation.constant.DonationStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Donation {

    private Integer id;
    private String name;
    private String location;
    private String phoneNumber;
    private Integer quantity;

    private LocalDateTime timeExpired;
    private String note;
    private String typeBlood;
    @Enumerated(EnumType.STRING)
    private DonationStatus status;
    private Integer donorId;
    private Integer mediaId;
    private Date createdAt;
    private Date modifiedAt;
    private Date deletedAt;
}
