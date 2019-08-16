package pl.marekk.sns.notification.application;

import io.vavr.control.Either;
import java.util.function.Function;
import pl.marekk.sns.model.CreateNotificationRequest;
import pl.marekk.sns.notification.domain.Notification;

class FakeNotification implements Notification {

  @Override
  public Either<String, String> push() {
    return Either.right("ok");
  }

  @Override
  public String getApplicationArn(Function<String, String> arnFinder) {
    return null;
  }

  @Override
  public String getDeviceToken() {
    return null;
  }

  @Override
  public CreateNotificationRequest getNotificationRequest() {
    return null;
  }
}
