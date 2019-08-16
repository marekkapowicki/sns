package pl.marekk.sns.notification.outbound;

import pl.marekk.sns.notification.domain.Notification;

public interface SnsEndpointFactory {

  String createDestinationEndpoint(Notification notification);
}
