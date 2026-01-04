package co.istab.blooddonationservice.domain.blood.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVerification {

    private Integer id;
    private Integer userId;
    private String verificationCode;
    private LocalDateTime expiryTime;
    private Date createdAt;
    private Date modifiedAt;
    private Date deletedAt;

}
