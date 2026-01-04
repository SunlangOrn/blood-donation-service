package co.istab.blooddonationservice.infrastructure.database.provider;

import co.istab.blooddonationservice.domain.blood.user.entity.Role;
import co.istab.blooddonationservice.domain.blood.user.entity.User;
import co.istab.blooddonationservice.domain.blood.user.provider.UserDatabaseProvider;
import co.istab.blooddonationservice.infrastructure.database.mapper.RoleDatabaseMapper;
import co.istab.blooddonationservice.infrastructure.database.mapper.UserDatabaseMapper;
import co.istab.blooddonationservice.infrastructure.database.mysql.Entity.UserEntity;
import co.istab.blooddonationservice.infrastructure.database.mysql.repository.RoelJpaRepository;
import co.istab.blooddonationservice.infrastructure.database.mysql.repository.UserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDatabaseProviderFacade implements UserDatabaseProvider  {

    private final UserJpaRepository jpaRepository;
    private final UserDatabaseMapper userMapper;
    private final RoelJpaRepository roelJpaRepository;
    private final RoleDatabaseMapper roleMapper;

    @Override
    @Transactional
    public Optional<Role> getRoleId(Integer roleId) {
        return roelJpaRepository
                .findById(roleId)
                .map(roleMapper::form);
    }

    @Override
    @Transactional
    public Optional<User> getByPhoneNumber(String phoneNumber) {
        return jpaRepository
                .findOne((root, query, criteriaBuilder) ->
                        criteriaBuilder.and(
                                criteriaBuilder.isNull(root.get("deletedAt")),
                                criteriaBuilder.equal(root.get("phoneNumber"), phoneNumber)))
                .map(userMapper::form);
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        return jpaRepository
                .findOne((root, query, criteriaBuilder) ->
                        criteriaBuilder.and(
                                criteriaBuilder.isNull(root.get("deletedAt")),
                                criteriaBuilder.equal(root.get("id"), id)))
                .map(userMapper::form);
    }

    @Override
    @Transactional
    public User save(User user) {
        UserEntity userEntity = userMapper.form(user);
        jpaRepository.save(userEntity);
        return userMapper.form(userEntity);
    }
}
