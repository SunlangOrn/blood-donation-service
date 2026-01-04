package co.istab.blooddonationservice.domain.blood.user.service;

import co.istab.blooddonationservice.domain.blood.user.entity.User;
import co.istab.blooddonationservice.share.entity.Metadata;
import org.springframework.data.jpa.repository.Meta;

import java.util.Optional;

public interface UserService {

    User view(Metadata metadata, Integer id);

    User update(Metadata metadata,Integer userId ,User user);



}
