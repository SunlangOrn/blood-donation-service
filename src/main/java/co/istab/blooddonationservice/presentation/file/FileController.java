package co.istab.blooddonationservice.presentation.file;

import co.istab.blooddonationservice.domain.file.File;
import co.istab.blooddonationservice.domain.file.FileService;
import co.istab.blooddonationservice.share.entity.HttpBodyResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static co.istab.blooddonationservice.share.api.ControllerHandler.responseCreated;
import static co.istab.blooddonationservice.share.api.ControllerHandler.responseDeleted;

@Slf4j
@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final FileControllerMapper mapper;

    @PostMapping(value = "/multiple/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SneakyThrows
    public ResponseEntity<HttpBodyResponse<List<FileResponse>>> uploadMultiple(@PathVariable Integer userId,
                                                                               @RequestPart List<MultipartFile> files){
        List<File> domain =fileService.uploadMultipleFiles(userId, files);
        List<FileResponse> response = mapper.form(domain);
        return responseCreated(response);
    }

    @PostMapping(value ="/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SneakyThrows
    public ResponseEntity<HttpBodyResponse<FileResponse>> uploadFile(
            @PathVariable Integer userId,
            @RequestPart MultipartFile file){

        File domain = fileService.uploadFile(userId, file);
        FileResponse response =mapper.form(domain);

        return responseCreated(response);
    }

    @DeleteMapping()
    @SneakyThrows
    public ResponseEntity<Void> delete(@RequestParam("url") String url){
        log.info("Starting delete file. -> URL: {}", url);
        fileService.delete(url);
        return responseDeleted();
    }
}
