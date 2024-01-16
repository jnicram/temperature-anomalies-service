package io.kontak.apps.anomaly.api;

import io.kontak.apps.anomaly.api.dao.AnomalyDaoService;
import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.ThermometerWithAnomaliesCount;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AnomalyEndpoint {

  private final AnomalyDaoService anomalyDaoService;

  @GetMapping("/anomalies/thermometer/{thermometerId}")
  public List<AnomalyDto> getAnomaliesByThermometerId(@PathVariable String thermometerId) {
    List<Anomaly> anomalies = anomalyDaoService.getAnomaliesByThermometerId(thermometerId);
    return anomalies.stream()
        .map(AnomalyDto::from)
        .toList();
  }

  @GetMapping("/anomalies/room/{roomId}")
  public List<AnomalyDto> getAnomaliesByRoomId(@PathVariable String roomId) {
    List<Anomaly> anomalies = anomalyDaoService.getAnomaliesByRoomId(roomId);
    return anomalies.stream()
        .map(AnomalyDto::from)
        .toList();
  }

  @GetMapping("/thermometers")
  public List<ThermometerWithAnomaliesCountDto> getThermometersByAnomalyThreshold(
      @RequestParam(value = "threshold", defaultValue = "10") Long threshold) {
    List<ThermometerWithAnomaliesCount> thermometers = anomalyDaoService.getThermometersByAnomalyThreshold(threshold);
    return thermometers.stream()
        .map(ThermometerWithAnomaliesCountDto::from)
        .toList();
  }
}
