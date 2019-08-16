package pl.marekk.sns.notification.outbound;


import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.aws.autoconfigure.context.properties.AwsRegionProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.localstack.LocalStackContainer.Service;

@Configuration
@Profile("test")
@Log4j2
public class SnsLocalConfiguration {


  @Bean
  public LocalStackContainer localStackContainer() {
    return new LocalStackContainer()
        .withServices(Service.SNS);
  }

  @MockBean
  AwsRegionProperties awsRegionProperties;

  @Bean
  public AmazonSNS amazonSNS(LocalStackContainer container) {
    container.start();
    EndpointConfiguration snsEndpointConfig = container.getEndpointConfiguration(Service.SNS);
    return AmazonSNSClientBuilder.standard()
        .withEndpointConfiguration(snsEndpointConfig)
        .withCredentials(container.getDefaultCredentialsProvider())
        .build();

  }

  @Bean
  SnsEndpointFactory snsEndpointFactory(LocalStackContainer container, AmazonSNS amazonSNS) {
    return new SnsLocalEndpointFactory(amazonSNS,
        container.getEndpointConfiguration(Service.SNS).getServiceEndpoint());
  }

}
