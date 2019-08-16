package pl.marekk.sns.notification.inbound;

import java.util.function.Function;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.marekk.sns.notification.domain.Notification;


@Configuration
public class InboundConfiguration {
  @Bean
  NotificationFactory notificationFactory(Function<Notification, String> sns) {
     return new NotificationFactory(sns);
  }
}
