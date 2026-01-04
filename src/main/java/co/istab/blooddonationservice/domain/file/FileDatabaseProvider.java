package co.istab.blooddonationservice.domain.file;

import java.util.Optional;

public interface FileDatabaseProvider {

    Optional<File> getById(Integer id);

    File saveFile(File file);
}
