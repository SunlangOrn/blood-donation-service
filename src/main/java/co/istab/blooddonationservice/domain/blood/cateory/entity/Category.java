package co.istab.blooddonationservice.domain.blood.cateory.entity;

import co.istab.blooddonationservice.domain.file.File;
import lombok.Data;

import java.util.Date;

@Data
public class Category {

    private Integer id;
    private String name;
    private File fileId;
    private Date createdAt;
    private Date modifiedAt;
    private Date deletedAt;
}
