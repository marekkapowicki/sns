package pl.marekk.sns.model;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.toomuchcoding.jsonassert.JsonAssertion;
import org.assertj.core.util.Lists;
import org.junit.Test;
import pl.marekk.sns.model.android.CreateAndroidNotificationRequest;
import pl.marekk.sns.model.ios.CreateIosNotificationRequest;

public class SnsPushNotificationFactoryTest {

  private SnsNotificationFactory snsNotificationFactory = SnsNotificationFactory.instance(new ObjectMapper());

  @Test
  public void shouldCreateIosNotification() {
    //Given
    String expectedJsonNotification = "{\"aps\":"
        + "{\"alert\":"
        + "{\"title\":\"sampleTitle\",\"body\":\"sampleBody\",\"title-loc-key\":\"sampleTitleLocKey\",\"title-loc-args\":[\"titleArg1\"],\"loc-key\":\"sampleLocKey\",\"loc-args\":[\"bodyArg1\"],\"content-available\":1}}}";
    CreateIosNotificationRequest request = CreateIosNotificationRequest.builder()
        .body("sampleBody")
        .title("sampleTitle")
        .titleLocKey("sampleTitleLocKey")
        .titleLocArgs(Lists.newArrayList("titleArg1"))
        .locKey("sampleLocKey")
        .locArgs(Lists.newArrayList("bodyArg1")).build();
    //When
    String json = snsNotificationFactory.create(request);
    //Then
    JsonAssertion.assertThat(json)
        .field("APNS").isEqualTo(expectedJsonNotification);
  }

  @Test
  public void shouldCreateAndroidNotification() {
    //Given
    String expectedJsonNotification = "{"
        + "\"notification\":{\"body\":\"sampleBody\",\"title\":\"sampleTitle\",\"body_loc_key\":\"sampleBodyLocKey\",\"body_loc_args\":[\"bodyArg1\"],\"title_loc_key\":\"sampleTitleLocKey\",\"title_loc_args\":[\"titleArg1\"]},"
        + "\"data\":{\"uri\":\"sampleuri\"}}";

    CreateAndroidNotificationRequest request = CreateAndroidNotificationRequest.builder()
        .body("sampleBody")
        .title("sampleTitle")
        .bodyLocKey("sampleBodyLocKey")
        .bodyLocArgs(Lists.newArrayList("bodyArg1"))
        .titleLocKey("sampleTitleLocKey")
        .titleLocArgs(Lists.newArrayList("titleArg1"))
        .deepLinkUri("sampleuri").build();

    //When
    String json = snsNotificationFactory.create(request);
    //Then
    JsonAssertion.assertThat(json)
        .field("GCM").isEqualTo(expectedJsonNotification);

  }
}