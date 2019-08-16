package pl.marekk.sns.model.android;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.marekk.sns.model.PhoneNotification;

@Getter
class AndroidNotificationWrapper {

  private final PhoneNotification notification;
  private final AndroidData data;

  @JsonCreator
  AndroidNotificationWrapper(@JsonProperty("notification") AndroidNotification notification) {
    this.data = AndroidData.from(notification);
    this.notification = notification;
  }

  @AllArgsConstructor
  @Getter
  private static class AndroidData {

    private final String uri;

    static AndroidData from(AndroidNotification androidNotification) {
      return new AndroidData(androidNotification.getDeepLinkUri());
    }
  }
}
