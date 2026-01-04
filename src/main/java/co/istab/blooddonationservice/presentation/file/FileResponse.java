package co.istab.blooddonationservice.presentation.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileResponse {

    private String fileName;
    private String fileType;
    private String fileUrl;
    private Long fileSize;
}
