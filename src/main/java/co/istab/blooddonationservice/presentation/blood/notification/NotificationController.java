package co.istab.blooddonationservice.presentation.blood.notification;

import co.istab.blooddonationservice.domain.blood.notification.entity.Notification;
import co.istab.blooddonationservice.domain.blood.notification.entity.ReadNotification;
import co.istab.blooddonationservice.domain.blood.notification.provider.NotificationDatabaseProvider;
import co.istab.blooddonationservice.domain.blood.notification.provider.ReadNotificationDatabaseProvider;
import co.istab.blooddonationservice.domain.blood.notification.service.NotificationService;
import co.istab.blooddonationservice.presentation.blood.notification.mapper.NotificationControllerMapper;
import co.istab.blooddonationservice.presentation.blood.notification.model.response.NotificationResponse;
import co.istab.blooddonationservice.presentation.blood.notification.model.response.NotificationResponseDetail;
import co.istab.blooddonationservice.share.entity.*;
import co.istab.blooddonationservice.share.handler.metadata.MetadataHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static co.istab.blooddonationservice.share.api.ControllerHandler.*;


@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationDatabaseProvider provider;
    private final ReadNotificationDatabaseProvider readProvider;
    private final NotificationControllerMapper mapper;

    @GetMapping
    @MetadataHandler
    public ResponseEntity<Paging<NotificationResponseDetail>> getMyNotifications(
            Metadata metadata,
            PaginationQuery query
    ) {

        Paging<Notification> paging = notificationService.list(metadata, query);

        Integer userId = Integer.parseInt(metadata.getUserId());

        List<NotificationResponseDetail> responses = paging.getItems().stream()
                .map(notification -> {
                    Optional<ReadNotification> readStatus =
                            readProvider.getUserIdAndNotificationId(userId, notification.getId());

                    return mapper.mapDetail(
                            notification,
                            readStatus.isPresent(),
                            readStatus.map(ReadNotification::getReadAt).orElse(null)
                    );
                })
                .toList();

        Paging<NotificationResponseDetail> responsePaging = Paging.<NotificationResponseDetail>builder()
                .items(responses)
                .page(paging.getPage())
                .size(paging.getSize())
                .total(paging.getTotal())
                .totalPages(paging.getTotalPages())
                .build();

        return ResponseEntity.ok(responsePaging);
    }


    @GetMapping("/unread")
    @MetadataHandler
    public ResponseEntity<HttpBodyResponse<List<NotificationResponse>>> unread(
            Metadata metadata,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        PaginationQuery query = PaginationQuery.of(page, size, null, null, null, null, null);

        Paging<Notification> paging = notificationService.unread(metadata, query);

      return responsePaging(
              paging.getItems().stream().map(notification ->
                      mapper.mapResponse(notification, false, null)).toList(),
              HttpBodyPagingResponse.of(
                      paging.getPage(),
                      paging.getSize(),
                      paging.getTotal(),
                      paging.getTotalPages()
              )
      );
    }


    @PatchMapping("/read/{id}")
    @MetadataHandler
    public ResponseEntity<HttpBodyResponse<NotificationResponseDetail>> viewDetail(
            Metadata metadata,
            @PathVariable Integer id
    ) {
        Notification notification = notificationService.viewNotification(metadata, id);

        Optional<ReadNotification> readStatus = readProvider
                .getUserIdAndNotificationId(Integer.parseInt(metadata.getUserId()), id);

        NotificationResponseDetail response = mapper.mapDetail(
                notification,
                readStatus.isPresent(),
                readStatus.map(ReadNotification::getReadAt).orElse(null)
        );

        return responseCreated(response);
    }
}
