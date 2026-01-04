package co.istab.blooddonationservice.domain.blood.user.application;

import co.istab.blooddonationservice.domain.blood.user.entity.User;
import co.istab.blooddonationservice.domain.blood.user.exception.UserException;
import co.istab.blooddonationservice.domain.blood.user.provider.UserDatabaseProvider;
import co.istab.blooddonationservice.domain.blood.user.service.UserService;
import co.istab.blooddonationservice.share.entity.Metadata;
import co.istab.blooddonationservice.share.handler.metadata.MetadataHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserServiceFacade implements UserService {

    private final UserDatabaseProvider provider;

    @Transactional
    @MetadataHandler
    @Override
    public User view(Metadata metadata, Integer id) {

        Integer authId = Integer.parseInt(metadata.getUserId());

        if (id != null && !authId.equals(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only view your own profile");
        }

        return provider.getUserById(authId)
                .orElseThrow(UserException::notFound);
    }

    @Transactional
    @Override
    @MetadataHandler
    public User update(Metadata metadata, Integer userId, User user) {

        Integer authId = Integer.parseInt(metadata.getUserId());

        if (!authId.equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }

        User oldEntity = provider.getUserById(authId)
                .orElseThrow(UserException::notFound);

        if (user.getFirstName() != null) oldEntity.setFirstName(user.getFirstName());
        if (user.getLastName() != null) oldEntity.setLastName(user.getLastName());
        if (user.getPhoneNumber() != null && !user.getPhoneNumber().isBlank()) {
            String newPhone = user.getPhoneNumber();

            if (newPhone.startsWith("0")) {
                newPhone = newPhone.substring(1);
            }

            oldEntity.setEmail(user.getEmail());
            oldEntity.setTypeBlood(user.getTypeBlood());

            oldEntity.setPhoneNumber(newPhone);
        }
        if (user.getLocation() != null) oldEntity.setLocation(user.getLocation());
        oldEntity.setModifiedAt(new Date());

        return provider.save(oldEntity);
    }
}
