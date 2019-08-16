package pl.marekk.sns.model.ios;

import com.fasterxml.jackson.annotation.JsonProperty;
import pl.marekk.sns.model.SnsNotification;

class ApnsNotification implements SnsNotification {
  private final String value;

  ApnsNotification(String value) {
    this.value = value;
  }

  @JsonProperty("APNS")
  @Override
  public String getValue() {
    return value;
  }
}
