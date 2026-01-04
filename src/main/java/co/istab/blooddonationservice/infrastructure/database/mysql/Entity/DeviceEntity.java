package co.istab.blooddonationservice.infrastructure.database.mysql.Entity;

import co.istab.blooddonationservice.share.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.Currency;

@Data
@Entity
@Table(name = "device")
public class DeviceEntity extends BaseEntity {

    @Column(name = "name")
    private String name;
    @Column(name = "token")
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;


}
