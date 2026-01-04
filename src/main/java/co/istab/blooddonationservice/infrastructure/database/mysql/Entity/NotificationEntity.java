package co.istab.blooddonationservice.infrastructure.database.mysql.Entity;

import co.istab.blooddonationservice.share.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "notification")
public class NotificationEntity extends BaseEntity {

    @Column(name = "title")
    private String title;
    @Column(name = "message")
    private String message;
    @Column(name = "type")
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_action_id")
    private DonationActionEntity referenceAction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_post_id")
    private PostEntity referencePost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_donation_id")
    private DonationEntity referenceDonation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
