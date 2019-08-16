package pl.marekk.sns.notification.outbound;

import java.util.Set;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "phone")
@Data
class PhoneBundle implements UnaryOperator<String> {

  private Set<Bundle> bundles;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  static class Bundle {
    private String key;
    private String platformApplicationArn;

    boolean equalUsingBundle(String bundle) {
      return this.key.equals(bundle);
    }

  }

  @Override
  public String apply(String bundleId) {
    return bundles.stream()
        .filter(bundle -> bundle.equalUsingBundle(bundleId))
        .findFirst()
        .map(Bundle::getPlatformApplicationArn)
        .orElseThrow(IllegalArgumentException::new);
  }

}
