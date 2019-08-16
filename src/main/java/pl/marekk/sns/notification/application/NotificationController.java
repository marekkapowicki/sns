package pl.marekk.sns.notification.application;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.vavr.control.Either;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.marekk.sns.notification.domain.NotificationRequest;
import pl.marekk.sns.notification.inbound.NotificationFactory;

@Slf4j
@RestController("api/notifications")
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class NotificationController {

  NotificationFactory notificationFactory;
   ModelMapper modelMapper;
  @PostMapping(consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> sendNotification(@RequestBody @NotNull @Validated SendNotificationCommand command) {
    log.info("sending the notification {}", command);
    Either<String, String> result = notificationFactory
        .createMessage(modelMapper.map(command, NotificationRequest.class))
        .push();
    return result.fold(
        exception ->
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(APPLICATION_JSON)
                .build(),
        ok -> ResponseEntity.ok()
            .contentType(APPLICATION_JSON)
            .build()
    );
  }


}
