package co.istab.blooddonationservice.domain.file;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class File {

    private Integer id;
    private String fileName;
    private String fileType;
    private Long fileSize;
    private String fileUrl;
    private Date createdAt;
    private Date modifiedAt;
    private Date deletedAt;


}
