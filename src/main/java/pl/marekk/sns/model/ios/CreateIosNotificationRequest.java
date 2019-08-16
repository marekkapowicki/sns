package pl.marekk.sns.model.ios;

import java.util.List;
import lombok.Builder;
import pl.marekk.sns.model.CreateNotificationRequest;
import pl.marekk.sns.model.PhoneNotification;

@Builder
public class CreateIosNotificationRequest implements CreateNotificationRequest {

  private final String title;
  private final String body;
  private final String titleLocKey;
  private final List<String> titleLocArgs;
  private final String locKey;
  private final List<String> locArgs;

  @Override
  public PhoneNotification toPhoneNotification() {
    return new IosNotification(title, body, titleLocKey, titleLocArgs, locKey, locArgs);
  }
}
