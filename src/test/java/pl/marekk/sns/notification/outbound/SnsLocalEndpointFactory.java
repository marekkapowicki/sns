package pl.marekk.sns.notification.outbound;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.SubscribeRequest;
import lombok.AllArgsConstructor;
import pl.marekk.sns.notification.domain.Notification;

@AllArgsConstructor
public class SnsLocalEndpointFactory implements SnsEndpointFactory {

  private final AmazonSNS amazonSNS;
  private final String serviceEndpoint;

  @Override
  public String createDestinationEndpoint(Notification notification) {
    CreateTopicResult topicResult = amazonSNS.createTopic(new CreateTopicRequest()
        .withName("topicName"));
    amazonSNS.subscribe(new SubscribeRequest()
        .withEndpoint(serviceEndpoint)
        .withProtocol("sns")
        .withTopicArn(topicResult.getTopicArn()));
    return topicResult.getTopicArn();
  }
}
