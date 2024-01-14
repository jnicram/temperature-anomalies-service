package io.kontak.apps;

import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.ThermometerWithAnomaliesCount;
import java.util.List;

public interface AnomalyRepository {

  List<Anomaly> findByThermometerId(String thermometerId);

  List<Anomaly> findByRoomId(String roomId);

  List<ThermometerWithAnomaliesCount> findThermometersByAnomalyThreshold(Long threshold);
}
