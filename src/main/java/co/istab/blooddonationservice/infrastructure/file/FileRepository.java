package co.istab.blooddonationservice.infrastructure.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface FileRepository extends JpaRepository<FileEntity,Integer>, JpaSpecificationExecutor<FileEntity> {

}
