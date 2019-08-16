package pl.marekk.sns.notification.inbound;

import java.util.function.Function;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import pl.marekk.sns.notification.domain.Notification;
import pl.marekk.sns.notification.domain.NotificationRequest;


@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class NotificationFactory {
  Function<Notification, String> snsFunction;

  public Notification createMessage(NotificationRequest request) {
    return request.toMessage(snsFunction);
  }
}
