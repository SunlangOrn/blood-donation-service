package co.istab.blooddonationservice.presentation.blood.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileResponse {

    private Integer userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String location;
    private String typeBlood;
    private String profile;
    private Boolean status;
    private Integer mediaId;
    private Date cratedAt;
    private Date modifiedAt;

}
