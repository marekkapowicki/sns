package pl.marekk.sns.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.function.Function;
import pl.marekk.sns.notification.domain.Notification;

/**
 * Creates the proper String representation for the Amazon SNS push request
 */
public class SnsNotificationFactory  implements Function<Notification, String> {
  private final SnsObjectMapper snsObjectMapper;
  public static SnsNotificationFactory instance(ObjectMapper objectMapper){
    return new SnsNotificationFactory(new SnsObjectMapper(objectMapper));
  }
  private SnsNotificationFactory(SnsObjectMapper snsObjectMapper) {
    this.snsObjectMapper = snsObjectMapper;
  }
  @Override
  public String apply(Notification notification) {
    return create(notification.getNotificationRequest());
  }

  String create(CreateNotificationRequest createNotificationRequest){
    final SnsNotification snsNotification = createNotificationRequest
        .toPhoneNotification()
        .toSnsNotification(snsObjectMapper);
    return snsObjectMapper.stringify(snsNotification);
  }


}
