package co.istab.blooddonationservice.domain.blood.password_reset.provider;

import co.istab.blooddonationservice.domain.blood.password_reset.entity.PasswordReset;

import java.util.Optional;

public interface PasswordResetDatabaseProvider {

    Optional<PasswordReset> findById(Integer id);

    Optional<PasswordReset> findCode(String code);

    PasswordReset save( PasswordReset passwordReset);
}
