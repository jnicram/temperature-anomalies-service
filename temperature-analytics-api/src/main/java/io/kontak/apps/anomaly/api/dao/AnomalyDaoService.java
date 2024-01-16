package io.kontak.apps.anomaly.api.dao;

import io.kontak.apps.AnomalyRepository;
import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.ThermometerWithAnomaliesCount;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnomalyDaoService {

  private final AnomalyRepository anomalyRepository;

  public List<Anomaly> getAnomaliesByThermometerId(String thermometerId) {
    return anomalyRepository.findByThermometerId(thermometerId);
  }

  public List<Anomaly> getAnomaliesByRoomId(String roomId) {
    return anomalyRepository.findByRoomId(roomId);
  }

  public List<ThermometerWithAnomaliesCount> getThermometersByAnomalyThreshold(Long threshold) {
    return anomalyRepository.findThermometersByAnomalyThreshold(threshold);
  }
}
