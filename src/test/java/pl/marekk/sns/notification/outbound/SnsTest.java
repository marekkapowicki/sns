package pl.marekk.sns.notification.outbound;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Function;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.junit4.SpringRunner;
import pl.marekk.sns.notification.application.PhoneType;
import pl.marekk.sns.notification.application.SnsApplication;
import pl.marekk.sns.notification.domain.Notification;
import pl.marekk.sns.notification.domain.NotificationRequest;
import pl.marekk.sns.notification.inbound.NotificationFactory;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SnsApplication.class)
@IfProfileValue(name = "spring.profiles.active", value = "prod")
public class SnsTest {

  @Autowired
  private Function<Notification, String> sns;

  @Autowired
  private NotificationFactory notificationFactory;

  @Test
  public void iosNotification() {
    //given
    NotificationRequest notificationRequest = NotificationRequest.builder()
        .bundleId("tst")
        .recipient("BF92B2C88EE8F9EC45D8777A3E56B8291791AD2403DE4C1B9F45AAA3F96A2685")
        .message("hoho")
        .type(PhoneType.IOS)
        .build();
    Notification message = notificationFactory.createMessage(notificationRequest);

    //when
    String result = sns.apply(message);

    //then
    assertThat(result).isNotEmpty();
  }
}