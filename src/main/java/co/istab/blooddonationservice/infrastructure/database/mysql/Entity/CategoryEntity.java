package co.istab.blooddonationservice.infrastructure.database.mysql.Entity;

import co.istab.blooddonationservice.infrastructure.file.FileEntity;
import co.istab.blooddonationservice.share.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "category")
public class CategoryEntity extends BaseEntity {

    @Column(name ="name")
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_id")
    private FileEntity file;


}
