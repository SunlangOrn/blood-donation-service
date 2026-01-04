package co.istab.blooddonationservice.domain.blood.notification.entity;

import co.istab.blooddonationservice.domain.blood.donation.entity.Donation;
import co.istab.blooddonationservice.domain.blood.donation_action.entity.DonationAction;
import co.istab.blooddonationservice.domain.blood.post.entity.Post;
import co.istab.blooddonationservice.domain.blood.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class Notification {

    private  Integer id;
    private  User user;

    private  String title;
    private  String message;
    private  String type;

    private  Donation referenceDonation;
    private  DonationAction referenceAction;
    private  Post referencePost;

    private Date createdAt;
    private Date modifiedAt;
    private Date deletedAt;

}
