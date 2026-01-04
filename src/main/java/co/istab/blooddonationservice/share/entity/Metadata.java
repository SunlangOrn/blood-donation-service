package co.istab.blooddonationservice.share.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.server.ResponseStatusException;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
public class Metadata {

  private String userId;
  private String  phoneNumber;
  private String role ;

    public static Metadata fromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }

        return Metadata.builder()
                    .userId(String.valueOf(jwt.getClaim("userId")))
                    .phoneNumber(jwt.getClaim("phoneNumber"))
                    .role(jwt.getClaim("scope"))
                    .build();
    }
}
