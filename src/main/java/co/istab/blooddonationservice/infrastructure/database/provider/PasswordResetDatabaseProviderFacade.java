package co.istab.blooddonationservice.infrastructure.database.provider;

import co.istab.blooddonationservice.domain.blood.password_reset.entity.PasswordReset;
import co.istab.blooddonationservice.infrastructure.database.mapper.PasswordResetDatabaseMapper;
import co.istab.blooddonationservice.infrastructure.database.mysql.Entity.PasswordResetEntity;
import co.istab.blooddonationservice.infrastructure.database.mysql.repository.PasswordResetJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordResetDatabaseProviderFacade implements co.istab.blooddonationservice.domain.blood.password_reset.provider.PasswordResetDatabaseProvider {

    private final PasswordResetJpaRepository jpaRepository;
    private final PasswordResetDatabaseMapper mapper;

    @Override
    public Optional<PasswordReset> findById(Integer id) {
        Specification<PasswordResetEntity> spec = (root, query, cb) -> cb.and(
                cb.isNull(root.get("deletedAt")),
                cb.equal(root.get("id"), id)
        );
        return jpaRepository.findOne(spec).map(mapper::form);
    }

    @Override
    public Optional<PasswordReset> findCode(String code) {
        Specification<PasswordResetEntity> spec = (root, query, cb) -> cb.and(
                cb.isNull(root.get("deletedAt")),
                cb.equal(root.get("code"), code)
        );
        return jpaRepository.findOne(spec).map(mapper::form);
    }

    @Override
    @Transactional
    public PasswordReset save(PasswordReset passwordReset) {
        PasswordResetEntity entity = mapper.form(passwordReset);
        PasswordResetEntity saveEntity = jpaRepository.save(entity);
        return mapper.form(saveEntity);
    }
}
