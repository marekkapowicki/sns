package pl.marekk.sns;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.marekk.sns.notification.application.SnsApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SnsApplication.class)
@ActiveProfiles("prod")
public class SnsApplicationTests {

	@Test
	public void contextLoads() {
	}

}
