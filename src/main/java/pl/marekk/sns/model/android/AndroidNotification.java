package pl.marekk.sns.model.android;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import pl.marekk.sns.notification.application.SnsMapper;
import pl.marekk.sns.model.PhoneNotification;
import pl.marekk.sns.model.SnsNotification;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
class AndroidNotification implements PhoneNotification {

  private final String body;
  private final String title;
  private final String bodyLocKey;
  private final List<String> bodyLocArgs;
  private final String titleLocKey;
  private final List<String> titleLocArgs;
  private final String deepLinkUri;

  @Override
  public SnsNotification toSnsNotification(SnsMapper snsObjectMapper) {
    AndroidNotificationWrapper notificationWrapper = new AndroidNotificationWrapper(this);
    return new GsmNotification(snsObjectMapper.stringify(notificationWrapper));
  }

  @JsonIgnore //the property is a part of "data" object
  String getDeepLinkUri() {
    return deepLinkUri;
  }
}
