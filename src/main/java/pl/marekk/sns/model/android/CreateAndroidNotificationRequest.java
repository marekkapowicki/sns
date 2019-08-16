package pl.marekk.sns.model.android;

import java.util.List;
import lombok.Builder;
import pl.marekk.sns.model.CreateNotificationRequest;
import pl.marekk.sns.model.PhoneNotification;

@Builder
public class CreateAndroidNotificationRequest implements CreateNotificationRequest {
  private final String body;
  private final String title;
  private final String bodyLocKey;
  private final List<String> bodyLocArgs;
  private final String titleLocKey;
  private final List<String> titleLocArgs;
  private final String deepLinkUri;

  @Override
  public PhoneNotification toPhoneNotification() {
    return new AndroidNotification(body, title, bodyLocKey, bodyLocArgs, titleLocKey, titleLocArgs,
        deepLinkUri);
  }
}
