package co.istab.blooddonationservice.infrastructure.file;

import co.istab.blooddonationservice.share.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "file_media")
public class FileEntity extends BaseEntity {

    @Column(name= "file_name")
    private String fileName;

    @Column(name= "file_type")
    private String fileType;

    @Column(name= "file_url")
    private String fileUrl;

    @Column(name= "file_size")
    private Long fileSize;

}
