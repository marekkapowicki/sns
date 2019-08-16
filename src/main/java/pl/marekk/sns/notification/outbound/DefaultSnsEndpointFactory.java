package pl.marekk.sns.notification.outbound;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.CreatePlatformEndpointRequest;
import com.amazonaws.services.sns.model.CreatePlatformEndpointResult;
import com.amazonaws.services.sns.model.InvalidParameterException;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.marekk.sns.notification.domain.Notification;

/**
 * Class creates the endpointArn  used to send the ios push notifications
 */
@AllArgsConstructor
@Slf4j
class DefaultSnsEndpointFactory implements SnsEndpointFactory {

  private final AmazonSNS amazonSNS;
  private final Function<String, String> arnFinder;

  /**
   * code copied from https://docs.aws.amazon.com/sns/latest/dg/mobile-platform-endpoint.html
   */
  @Override
  public String createDestinationEndpoint(Notification notification) {

    String endpointArn = null;
    try {
      log.debug("Creating platform endpoint with token {}", notification.getDeviceToken());
      CreatePlatformEndpointRequest cpeReq =
          new CreatePlatformEndpointRequest()
              .withPlatformApplicationArn(notification.getApplicationArn(arnFinder))
              .withToken(notification.getDeviceToken());
      CreatePlatformEndpointResult cpeRes = amazonSNS
          .createPlatformEndpoint(cpeReq);
      endpointArn = cpeRes.getEndpointArn();
    } catch (InvalidParameterException ipe) {
      String message = ipe.getErrorMessage();
      log.error("Exception message: " + message);
      Pattern p = Pattern
          .compile(".*Endpoint (arn:aws:sns[^ ]+) already exists " +
              "with the same token.*");
      Matcher m = p.matcher(message);
      if (m.matches()) {
        // The platform endpoint already exists for this token, but with
        // additional custom data that
        // createEndpoint doesn't want to overwrite. Just use the
        // existing platform endpoint.
        endpointArn = m.group(1);
      } else {
        // Rethrow the exception, the input is actually bad.
        throw ipe;
      }
    }
    return endpointArn;
  }
}
