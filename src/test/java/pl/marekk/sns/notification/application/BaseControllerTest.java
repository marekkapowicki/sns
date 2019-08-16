package pl.marekk.sns.notification.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.marekk.sns.notification.inbound.NotificationFactory;
import pl.marekk.sns.notification.outbound.SnsLocalConfiguration;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = {SnsLocalConfiguration.class,
    SnsApplication.class}, webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class BaseControllerTest {

  @LocalServerPort
  private int serverPort;

  @MockBean
  NotificationFactory factory;

  @Autowired
  private NotificationController notificationController;

  @Before
  public void setUp() {
    given(factory.createMessage(any()))
        .willReturn(new FakeNotification());
    RestAssuredMockMvc.standaloneSetup(notificationController);
    RestAssured.port = serverPort;
  }
}
