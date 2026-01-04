package co.istab.blooddonationservice.domain.file;

import co.istab.blooddonationservice.domain.blood.user.entity.User;
import co.istab.blooddonationservice.domain.blood.user.provider.UserDatabaseProvider;
import co.istab.blooddonationservice.presentation.file.FileResponse;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileServiceFacade implements FileService {

    private final FileDatabaseProvider fileDatabaseProvider;
    private final UserDatabaseProvider userDatabaseProvider;

    private final MinioClient  minioClient;

    @Value("${minio.bucket}")
    private String bucketName;

    @Value("${minio.url}")
    private String minioUrl;

    @Override @SneakyThrows
    @Transactional
    public List<File> uploadMultipleFiles(Integer userId , List<MultipartFile> files) {

        if (files == null || files.isEmpty()) {
            throw FileException.required();
        }

        List<File> list = new ArrayList<>();
        for (MultipartFile file : files) {
            list.add(uploadFile(userId, file));
        }
        return list;
    }

    @Override @SneakyThrows
    @Transactional
    public File uploadFile(Integer userId, MultipartFile file){

        if(file == null || file.isEmpty()) {
            throw FileException.required();
        }

        User user = userDatabaseProvider.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        String fileUrl = minioUrl + "/" + bucketName + "/" + fileName;

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .contentType(file.getContentType())
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .build()
        );


        File saveFile = fileDatabaseProvider.saveFile(File.builder()
                .fileName(fileName)
                .fileType(file.getContentType())
                .fileSize(file.getSize())
                .fileUrl(fileUrl)

                .build());

        user.setMediaId(saveFile.getId());
        user.setProfile(fileUrl);
        userDatabaseProvider.save(user);

        return saveFile;

    }

    @Override @SneakyThrows
    public void delete(String fileUrl){

        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);

        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build()
        );
    }
}
