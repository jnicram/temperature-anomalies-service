package io.kontak.apps.anomaly.api.dao;

import io.kontak.apps.AnomalyRepository;
import io.kontak.apps.anomaly.storage.dao.AnomalyEntity;
import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.ThermometerWithAnomaliesCount;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class JpaAnomalyRepository implements AnomalyRepository {

  private final JpaSpringAnomalyRepository springAnomalyRepository;

  public List<Anomaly> findByThermometerId(String thermometerId) {
    List<AnomalyEntity> entities = springAnomalyRepository.findByThermometerId(thermometerId);
    return entities.stream()
        .map(this::map)
        .collect(Collectors.toList());
  }

  public List<Anomaly> findByRoomId(String roomId) {
    List<AnomalyEntity> entities = springAnomalyRepository.findByRoomId(roomId);
    return entities.stream()
        .map(this::map)
        .collect(Collectors.toList());
  }

  public List<ThermometerWithAnomaliesCount> findThermometersByAnomalyThreshold(Long threshold) {
    List<AnomalyEntity> anomalies = springAnomalyRepository.findAll();
    return anomalies.stream()
        .collect(Collectors.groupingBy(AnomalyEntity::getThermometerId, Collectors.counting()))
        .entrySet().stream()
        .filter(entry -> entry.getValue() > threshold)
        .map(entry -> new ThermometerWithAnomaliesCount(entry.getKey(), entry.getValue()))
        .collect(Collectors.toList());
  }

  private Anomaly map(AnomalyEntity entity) {
    return new Anomaly(entity.getTemperature(), entity.getRoomId(), entity.getThermometerId(), entity.getTimestamp());
  }
}
