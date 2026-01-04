package co.istab.blooddonationservice.infrastructure.database.mysql.Entity;

import co.istab.blooddonationservice.share.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Table(name ="user_verification")
public class UserVerificationEntity extends BaseEntity {

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "expiry_time")
    private LocalDateTime expiryTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
