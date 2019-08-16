package pl.marekk.sns.model.ios;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
class IosNotificationWrapper {

  private final IosNotification aps;

  @JsonCreator
  IosNotificationWrapper(@JsonProperty("aps") IosNotification notification) {
    aps = notification;
  }

}