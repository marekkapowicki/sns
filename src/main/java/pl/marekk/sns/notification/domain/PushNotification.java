package pl.marekk.sns.notification.domain;

import io.vavr.control.Either;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import pl.marekk.sns.model.CreateNotificationRequest;

@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PushNotification implements Notification {

  Function<Notification, String> snsPush;
  CreateNotificationRequest notificationRequest;
  String deviceToken;
  String bundleId;

  @Override
  public Either<String, String> push() {
    return push(snsPush);
  }
  @Override
  public String getApplicationArn(Function<String, String> arnFinder) {
    return arnFinder.apply(bundleId);
  }

  @Override
  public String getDeviceToken() {
    return deviceToken;
  }

  @Override
  public CreateNotificationRequest getNotificationRequest() {
    return notificationRequest;
  }


  private Either<String, String> push(Function<Notification, String> function) {
    try {
      String result = apply(function);
      return Either.right(result);
    } catch(RuntimeException e) {
      log.error("exception is thrown", e);
      return Either.left(e.getMessage());
    }
  }
  private <T> T apply(Function<Notification, T> function){
    return function.apply(this);
  }

}
