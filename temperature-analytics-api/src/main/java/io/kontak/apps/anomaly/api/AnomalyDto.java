package io.kontak.apps.anomaly.api;

import io.kontak.apps.event.Anomaly;
import java.time.Instant;

public record AnomalyDto(double temperature, String roomId, String thermometerId, Instant timestamp) {

  public static AnomalyDto from(Anomaly anomaly) {
    return new AnomalyDto(anomaly.temperature(), anomaly.roomId(), anomaly.thermometerId(), anomaly.timestamp());
  }
}
