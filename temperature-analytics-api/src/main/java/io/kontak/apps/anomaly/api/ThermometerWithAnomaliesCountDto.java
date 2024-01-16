package io.kontak.apps.anomaly.api;

import io.kontak.apps.event.ThermometerWithAnomaliesCount;

public record ThermometerWithAnomaliesCountDto(String thermometerId, Long anomaliesCount) {

  public static ThermometerWithAnomaliesCountDto from(ThermometerWithAnomaliesCount thermometerWithAnomaliesCount) {
    return new  ThermometerWithAnomaliesCountDto(thermometerWithAnomaliesCount.thermometerId(),
        thermometerWithAnomaliesCount.anomaliesCount());
  }
}
