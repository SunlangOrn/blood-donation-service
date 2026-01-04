package co.istab.blooddonationservice.infrastructure.Config.Security;

import co.istab.blooddonationservice.domain.blood.user.entity.User;
import co.istab.blooddonationservice.infrastructure.database.provider.UserDatabaseProviderFacade;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDatabaseProviderFacade repository;

    @Override
    @NonNull
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {

        String normalizedPhoneNumber = normalizePhoneNumber(phoneNumber);

        User user = repository.getByPhoneNumber(normalizedPhoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with phoneNumber : " + phoneNumber));

        CustomerUserDetail customerUserDetail = new CustomerUserDetail();
        customerUserDetail.setUser(user);
        return customerUserDetail;
    }

    private String normalizePhoneNumber(String phoneNumber) {
        if (phoneNumber == null) return null;
        String normalized = phoneNumber.trim().replaceAll("[\\s\\-()]", "");
        if (normalized.startsWith("0")) normalized = normalized.substring(1);
        return normalized;
    }
}
