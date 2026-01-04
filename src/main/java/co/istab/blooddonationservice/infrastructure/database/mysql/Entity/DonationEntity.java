package co.istab.blooddonationservice.infrastructure.database.mysql.Entity;

import co.istab.blooddonationservice.domain.blood.donation.constant.DonationStatus;
import co.istab.blooddonationservice.infrastructure.file.FileEntity;
import co.istab.blooddonationservice.share.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Table(name ="donation")
public class DonationEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "time_expired")
    private LocalDateTime timeExpired;

    @Column(name = "note", nullable = true, length = 200)
    private String note;

    @Column(name = "type_blood")
    private String typeBlood;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DonationStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donor_id")
    private UserEntity donor;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_id")
    private FileEntity file;
}
