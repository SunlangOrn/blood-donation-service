package co.istab.blooddonationservice.share.handler.metadata;

import co.istab.blooddonationservice.share.entity.Metadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class MetadataHandlerAspect {

    @Before("@annotation(metadataHandler) && args(metadata,..)")
    public void handleMetadata(JoinPoint joinPoint, MetadataHandler metadataHandler, Metadata metadata) {
        log.info("========== METADATA HANDLER START ==========");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }

        if (!(authentication.getPrincipal() instanceof Jwt jwt)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid authentication type");
        }

        Object userIdClaim = jwt.getClaim("userId");
        String userId = userIdClaim != null ? String.valueOf(userIdClaim) : null;

        String phoneNumber = jwt.getClaim("phoneNumber");
        String scope = jwt.getClaim("scope");

        metadata.setUserId(userId);
        metadata.setPhoneNumber(phoneNumber);
        metadata.setRole(scope);

        log.info("User ID: {}", userId);
        log.info("Phone Number: {}", phoneNumber);
        log.info("Role/Scope: {}", scope);


        log.info("========== METADATA HANDLER END ==========");
    }
}
