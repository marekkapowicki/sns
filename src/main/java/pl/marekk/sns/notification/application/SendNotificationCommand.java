package pl.marekk.sns.notification.application;



import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
class SendNotificationCommand {

  @NotNull String message;
  String subject;
  @NotNull String recipient;
  @NotNull String bundleId;
  String bodyLocKey;
  List<String> bodyLocArgs;
  String titleLocKey;
  List<String> titleLocArgs;
  String deepLinkUri;
  @NotNull PhoneType type;


}
