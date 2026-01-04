package co.istab.blooddonationservice.domain.blood.password_reset.entity;

import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

@Data
public class PasswordReset {

    private Integer id;
    private Integer userId;
    private String code;
    private LocalTime expiryTime;
    private Date createdAt;
    private Date modifiedAt;
    private Date deletedAt;

}
