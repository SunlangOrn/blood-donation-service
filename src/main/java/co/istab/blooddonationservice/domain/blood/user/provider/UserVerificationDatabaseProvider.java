package co.istab.blooddonationservice.domain.blood.user.provider;

import co.istab.blooddonationservice.domain.blood.user.entity.UserVerification;

import java.util.Optional;

public interface UserVerificationDatabaseProvider {

    UserVerification save (UserVerification userVerification);

    Optional<UserVerification> findByUserId(Integer userId);

    void delete(UserVerification userVerification);
}
