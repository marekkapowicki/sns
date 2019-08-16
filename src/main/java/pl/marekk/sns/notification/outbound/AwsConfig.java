package pl.marekk.sns.notification.outbound;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.aws.autoconfigure.context.properties.AwsCredentialsProperties;
import org.springframework.cloud.aws.autoconfigure.context.properties.AwsRegionProperties;

@Data
@ConfigurationProperties(prefix = "cloud.aws")
class AwsConfig {
  private final AwsCredentialsProperties credentials;
  private final AwsRegionProperties region;

  AWSCredentials getAwsCredential() {
    return new BasicAWSCredentials(credentials.getAccessKey(), credentials.getSecretKey());
  }
  String getRegion() {
    return region.getStatic();
  }

}
