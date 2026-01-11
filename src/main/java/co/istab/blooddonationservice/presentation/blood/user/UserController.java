package co.istab.blooddonationservice.presentation.blood.user;

import co.istab.blooddonationservice.domain.blood.user.entity.User;
import co.istab.blooddonationservice.domain.blood.user.service.UserService;
import co.istab.blooddonationservice.presentation.blood.user.mapper.UserControllerMapper;
import co.istab.blooddonationservice.presentation.blood.user.model.request.UpdateUserProfileRequest;
import co.istab.blooddonationservice.presentation.blood.user.model.response.UserProfileResponse;
import co.istab.blooddonationservice.share.entity.HttpBodyResponse;
import co.istab.blooddonationservice.share.entity.Metadata;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static co.istab.blooddonationservice.share.api.ControllerHandler.responseCreated;
import static co.istab.blooddonationservice.share.api.ControllerHandler.responseSucceed;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserControllerMapper mapper;

    @GetMapping("/profile/{userId}")
    public ResponseEntity<HttpBodyResponse<UserProfileResponse>> view
            (Metadata metadata, @PathVariable Integer userId){
        return responseCreated(mapper.from(userService.view(metadata, userId)));
    }

    @PutMapping("/profile/{userId}")
    public ResponseEntity<HttpBodyResponse<UserProfileResponse>>update
            (@Valid@RequestBody UpdateUserProfileRequest request,
            @PathVariable Integer userId)
    {
        User user =mapper.from(request);
        return responseSucceed(
                mapper.from(userService.update(new Metadata(), userId ,user))
        );
    }
}

