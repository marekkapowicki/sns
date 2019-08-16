package pl.marekk.sns.model.ios;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import pl.marekk.sns.notification.application.SnsMapper;
import pl.marekk.sns.model.PhoneNotification;
import pl.marekk.sns.model.SnsNotification;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@JsonTypeName("alert") //root requires by ios
@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
class IosNotification implements PhoneNotification {

  private final String title;
  private final String body;
  private final String titleLocKey;
  private final List<String> titleLocArgs;
  private final String locKey;
  private final List<String> locArgs;
  private final Integer contentAvailable = 1;

  @Override
  public SnsNotification toSnsNotification(SnsMapper snsObjectMapper) {
    IosNotificationWrapper notificationWrapper = new IosNotificationWrapper(this);
    return new ApnsNotification(snsObjectMapper.stringify(notificationWrapper));
  }
}
