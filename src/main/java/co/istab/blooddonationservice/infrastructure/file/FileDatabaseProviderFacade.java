package co.istab.blooddonationservice.infrastructure.file;

import co.istab.blooddonationservice.domain.file.File;
import co.istab.blooddonationservice.domain.file.FileDatabaseProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileDatabaseProviderFacade implements FileDatabaseProvider {


    private final FileRepository repository;
    private final FileDatabaseMapper mapper;

    @Override
    public Optional<File> getById(Integer id) {
        return repository.findOne((root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.isNull(root.get("deletedAt")),
                        criteriaBuilder.equal(root.get("id"), id)
                )).map(mapper::form);
    }

    @Override
    public File saveFile(File file) {
        FileEntity fileEntity = mapper.form(file);
        return mapper.form(repository.save(fileEntity));
    }
}
