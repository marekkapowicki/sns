package pl.marekk.sns.notification.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class Requests {

  private static SendNotificationCommand validIosCommand = new SendNotificationCommand(
      "sampleMessage", "sampleSubject", "device_token", "bundleKey", null, new ArrayList<>(), null,
      new ArrayList<>(), null, PhoneType.IOS);

  static Request validIosCommand() {
    return new Request(validIosCommand);
  }


  @AllArgsConstructor
  static class Request {

    private final SendNotificationCommand command;

    SendNotificationCommand asObject() {
      return command;
    }

    String asJson() {
      try {
        return new ObjectMapper().writeValueAsString(command);
      } catch (JsonProcessingException e) {
        throw new IllegalStateException(e);
      }
    }
  }
}
