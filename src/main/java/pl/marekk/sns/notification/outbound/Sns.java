package pl.marekk.sns.notification.outbound;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.EndpointDisabledException;
import com.amazonaws.services.sns.model.InvalidParameterException;
import com.amazonaws.services.sns.model.InvalidParameterValueException;
import com.amazonaws.services.sns.model.NotFoundException;
import com.amazonaws.services.sns.model.PlatformApplicationDisabledException;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.marekk.sns.model.SnsNotificationFactory;
import pl.marekk.sns.notification.domain.Notification;

/***
 * Thread safe Amazon Simple PushNotification Service provider. Used to send iOS notifications.
 */
@Log4j2
@AllArgsConstructor
public class Sns implements Function<Notification, String> {

  private SnsNotificationFactory snsNotificationFactory;
  private final SnsEndpointFactory snsEndpointFactory;
  private final AmazonSNS amazonSNS;

  @Override
  public String apply(Notification notification) {

    String destinationName = snsEndpointFactory
        .createDestinationEndpoint(notification);
    String json = snsNotificationFactory.apply(notification);

    try {
      PublishResult result = amazonSNS.publish(new PublishRequest()
          .withTargetArn(destinationName)
          .withMessageStructure("json")
          .withMessage(json));
      log.debug("the notification has been sent");
      return result.getMessageId();
    } catch (NotFoundException | PlatformApplicationDisabledException | InvalidParameterException | InvalidParameterValueException | EndpointDisabledException e) {
      log.debug("something went wrong {}", e.getMessage());
      throw e;

    } catch (RuntimeException e) {
      log.error("error during sending the push notification. The Exception was: {}", () -> e);
      throw e;
    }
  }

}
