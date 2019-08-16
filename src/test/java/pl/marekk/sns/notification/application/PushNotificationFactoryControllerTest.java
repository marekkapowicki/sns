package pl.marekk.sns.notification.application;


import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class PushNotificationFactoryControllerTest extends BaseControllerTest {

  @Test
  public void return200ForValidRequest() {
    //expect
    given()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(Requests.validIosCommand().asJson())
    .when()
        .post("/api/notifications")
    .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(ContentType.JSON);
  }
}