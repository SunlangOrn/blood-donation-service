package co.istab.blooddonationservice.domain.blood.user.provider;


import co.istab.blooddonationservice.domain.blood.user.entity.Role;
import co.istab.blooddonationservice.domain.blood.user.entity.User;

import java.util.Optional;

public interface UserDatabaseProvider {

    Optional<User> getByPhoneNumber(String phoneNumber);

    Optional<User> getUserById(Integer id);

    Optional<Role> getRoleId(Integer roleId);

    User save(User user);
}
