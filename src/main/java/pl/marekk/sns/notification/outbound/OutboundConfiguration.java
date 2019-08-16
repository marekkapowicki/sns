package pl.marekk.sns.notification.outbound;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.function.Function;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.marekk.sns.model.SnsNotificationFactory;
import pl.marekk.sns.notification.domain.Notification;

@Configuration
@EnableConfigurationProperties({PhoneBundle.class, AwsConfig.class})
public class OutboundConfiguration {

  @Bean
  SnsNotificationFactory snsNotificationFactory(ObjectMapper objectMapper) {
    return SnsNotificationFactory.instance(objectMapper);
  }

  @Bean
  @Profile("!test")
  AmazonSNS amazonSNS(AwsConfig config) {
    return AmazonSNSClientBuilder.standard()
        .withCredentials(new AWSStaticCredentialsProvider(config.getAwsCredential()))
        .withRegion(config.getRegion()).build();
  }
  @Bean
  @Profile("!test")
  SnsEndpointFactory snsEndpointFactory(AmazonSNS amazonSNS, PhoneBundle phoneBundle) {
    return new DefaultSnsEndpointFactory(amazonSNS, phoneBundle);
  }

  @Bean
  Function<Notification, String> sns(
      SnsNotificationFactory snsNotificationFactory,
      SnsEndpointFactory snsEndpointFactory,
      AmazonSNS amazonSNS) {
    return new Sns(snsNotificationFactory, snsEndpointFactory, amazonSNS);
  }


}
