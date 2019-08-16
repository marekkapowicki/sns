package pl.marekk.sns.model.android;

import com.fasterxml.jackson.annotation.JsonProperty;
import pl.marekk.sns.model.SnsNotification;

class GsmNotification implements SnsNotification {

  private final String value;

  GsmNotification(String value) {
    this.value = value;
  }

  @JsonProperty("GCM")
  @Override
  public String getValue() {
    return value;
  }
}
