package pl.marekk.sns.notification.domain;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pl.marekk.sns.model.CreateNotificationRequest;
import pl.marekk.sns.model.android.CreateAndroidNotificationRequest;
import pl.marekk.sns.model.ios.CreateIosNotificationRequest;
import pl.marekk.sns.notification.application.PhoneType;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class NotificationRequest {
  private final Map<PhoneType, Supplier<CreateNotificationRequest>> phoneMapping =
      ImmutableMap.<PhoneType, Supplier<CreateNotificationRequest>>builder().
          put(PhoneType.IOS, this::iosRequest ).
          put(PhoneType.ANDROID, this::androidRequest).build();
  String message;
  String subject;
  String recipient;
  String bundleId;
  String bodyLocKey;
  List<String> bodyLocArgs;
  String titleLocKey;
  List<String> titleLocArgs;
  String deepLinkUri;
  PhoneType type;

  public Notification toMessage(Function<Notification, String> snsFunction) {
    return new PushNotification(
        snsFunction,
        getNotificationRequest(),
        recipient,
        bundleId);
  }

  private CreateNotificationRequest getNotificationRequest() {
    return Optional.ofNullable(phoneMapping.get(type))
        .map(Supplier::get)
        .orElseThrow(IllegalStateException::new);
  }

  private CreateNotificationRequest iosRequest() {
    return CreateIosNotificationRequest.builder()
        .title(subject)
        .body(message)
        .titleLocKey(titleLocKey)
        .titleLocArgs(titleLocArgs)
        .locKey(bodyLocKey)
        .titleLocArgs(bodyLocArgs)
        .build();
  }
  private CreateNotificationRequest androidRequest() {
    return CreateAndroidNotificationRequest.builder()
        .title(subject)
        .body(message)
        .titleLocKey(titleLocKey)
        .titleLocArgs(titleLocArgs)
        .bodyLocKey(bodyLocKey)
        .bodyLocArgs(bodyLocArgs)
        .deepLinkUri(deepLinkUri).build();
  }


}
