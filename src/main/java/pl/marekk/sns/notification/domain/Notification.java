package pl.marekk.sns.notification.domain;

import io.vavr.control.Either;
import java.util.function.Function;
import pl.marekk.sns.model.CreateNotificationRequest;

public interface Notification {

  Either<String, String> push();

  String getApplicationArn(Function<String, String> arnFinder);
  String getDeviceToken();
  CreateNotificationRequest getNotificationRequest();
}
