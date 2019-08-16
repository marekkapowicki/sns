package pl.marekk.sns.notification.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import pl.marekk.sns.notification.inbound.InboundConfiguration;
import pl.marekk.sns.notification.outbound.OutboundConfiguration;

@SpringBootApplication
@Import({InboundConfiguration.class, OutboundConfiguration.class})
public class SnsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnsApplication.class, args);
	}

}
