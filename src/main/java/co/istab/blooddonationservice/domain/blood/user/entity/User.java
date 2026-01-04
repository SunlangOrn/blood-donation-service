package co.istab.blooddonationservice.domain.blood.user.entity;

import co.istab.blooddonationservice.domain.file.File;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String phoneCode;
    private Role role;
    private String email;
    private String password;
    private String location;
    private String typeBlood;
    private String profile;
    private Boolean status;
    private Boolean isVerifyUser;
    private Boolean isVerifyOtp;
    private Date createdAt;
    private Date modifiedAt;
    private Date deletedAt;
    private Integer mediaId;

}
