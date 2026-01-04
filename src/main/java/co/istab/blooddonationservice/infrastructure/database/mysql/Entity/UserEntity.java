package co.istab.blooddonationservice.infrastructure.database.mysql.Entity;

import co.istab.blooddonationservice.infrastructure.file.FileEntity;
import co.istab.blooddonationservice.share.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity {

    @Column(name ="firstname", nullable = false)
    private String firstName;

    @Column(name ="lastname", nullable = false)
    private String lastName;

    @Column(name ="phone_number", nullable = false)
    private String phoneNumber;

    @Column(name ="phone_code", nullable = false)
    private String phoneCode;


    @Column(name ="email")
    private String email;

    @Column(name ="password")
    private String password;

    @Column(name ="type_blood")
    private String typeBlood;

    @Column(name ="profile")
    private String profile;

    @Column(name ="location")
    private String location;

    @Column(name ="status")
    private Boolean status;

    @Column(name ="is_verify_user")
    private Boolean isVerifyUser;

    @Column(name ="is_verify_otp")
    private Boolean isVerifyOtp;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "media_id")
    private FileEntity file;

    @OneToMany(mappedBy = "user")
    private List<DeviceEntity> devices;

    @OneToMany(mappedBy = "donor")
    private List<DonationEntity> donations;

    @OneToMany(mappedBy = "user")
    private List<DonationActionEntity> donationActions;

    @OneToMany(mappedBy = "user")
    private List<NotificationEntity> notifications;

}
