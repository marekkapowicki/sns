package pl.marekk.sns.notification.outbound;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Function;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.localstack.LocalStackContainer;
import pl.marekk.sns.notification.application.PhoneType;
import pl.marekk.sns.notification.application.SnsApplication;
import pl.marekk.sns.notification.domain.Notification;
import pl.marekk.sns.notification.domain.NotificationRequest;
import pl.marekk.sns.notification.inbound.NotificationFactory;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SnsLocalConfiguration.class, SnsApplication.class})
@ActiveProfiles("test")
//@IfProfileValue(name = "spring.profiles.active", value = "test")
public class SnsLocalTest {

  @Autowired
  private Function<Notification, String> sns;

  @Autowired
  private NotificationFactory notificationFactory;

  @Autowired
  private LocalStackContainer localStackContainer;

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
    localStackContainer.stop();
  }

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