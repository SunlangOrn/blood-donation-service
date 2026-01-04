package co.istab.blooddonationservice.infrastructure.database.provider;

import co.istab.blooddonationservice.domain.blood.user.entity.UserVerification;
import co.istab.blooddonationservice.domain.blood.user.provider.UserVerificationDatabaseProvider;
import co.istab.blooddonationservice.infrastructure.database.mapper.UserVerificationDatabaseMapper;
import co.istab.blooddonationservice.infrastructure.database.mysql.Entity.UserEntity;
import co.istab.blooddonationservice.infrastructure.database.mysql.Entity.UserVerificationEntity;
import co.istab.blooddonationservice.infrastructure.database.mysql.repository.UserJpaRepository;
import co.istab.blooddonationservice.infrastructure.database.mysql.repository.UserVerificationJpaRepository;
import co.istab.blooddonationservice.presentation.blood.user_verification.mapper.UserVerificationMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserVerificationDatabaseProviderFacade implements UserVerificationDatabaseProvider {

    private final UserVerificationJpaRepository jpaRepository;
    private final UserVerificationDatabaseMapper mapper;

    @Override
    public Optional<UserVerification> findByUserId(Integer userId) {
        Specification<UserVerificationEntity> spec = (root, query, cb) -> cb.and(
                cb.isNull(root.get("deletedAt")),
                cb.equal(root.get("user").get("id"), userId)
        );

        return jpaRepository.findOne(spec)
                .map(mapper::form);
    }

    @Override
    @Transactional
    public UserVerification save(UserVerification userVerification) {
        UserVerificationEntity entity = mapper.form(userVerification);
        UserVerificationEntity saved = jpaRepository.save(entity);
        return mapper.form(saved);
    }

    @Override
    @Transactional
    public void delete(UserVerification userVerification) {
        userVerification.setModifiedAt(new Date());
        userVerification.setDeletedAt(new Date());
        save(userVerification);
    }
}
