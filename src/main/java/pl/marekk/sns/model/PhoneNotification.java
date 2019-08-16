package pl.marekk.sns.model;

import pl.marekk.sns.notification.application.SnsMapper;

public interface PhoneNotification {

  /**
   * Unfortunately amazonSNS requires the String as a push notification parameters
   * @return String representation of Amazon SNS request
   */
  SnsNotification toSnsNotification(SnsMapper snsObjectMapper);
}
