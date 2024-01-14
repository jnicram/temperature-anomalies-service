package io.kontak.apps.anomaly.storage.dao;

import io.kontak.apps.event.Anomaly;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class JpaAnomalyRepository {

  private final JpaSpringAnomalyRepository springAnomalyRepository;

  public void insert(Anomaly anomaly) {
    Optional<AnomalyEntity> anomalyEntity = springAnomalyRepository.findByRoomIdAndThermometerIdAndTimestamp(
        anomaly.roomId(), anomaly.thermometerId(), anomaly.timestamp());

    if (anomalyEntity.isEmpty()) {
      springAnomalyRepository.save(AnomalyEntity.builder()
          .roomId(anomaly.roomId())
          .thermometerId(anomaly.thermometerId())
          .temperature(anomaly.temperature())
          .timestamp(anomaly.timestamp())
          .build());
    }
  }
}
