package io.kontak.apps.anomaly.detector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.TemperatureReading;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class AnomalyDetectionAlgorithmTwoTest {

    private final AnomalyDetectionAlgorithmTwo detector = new AnomalyDetectionAlgorithmTwo();

    @Test
    void applyWhenTemperatureIsNormalThenNoAnomaly() {
        // given
        List<TemperatureReading> temperatureReadings = Arrays.asList(
            new TemperatureReading(21.2, "room", "thermo", Instant.now()),
            new TemperatureReading(20.0, "room", "thermo", Instant.now().plusSeconds(1)),
            new TemperatureReading(21.1, "room", "thermo", Instant.now().plusSeconds(2)),
            new TemperatureReading(21.2, "room", "thermo", Instant.now().plusSeconds(3)),
            new TemperatureReading(22.0, "room", "thermo", Instant.now().plusSeconds(4)),
            new TemperatureReading(21.2, "room", "thermo", Instant.now().plusSeconds(5))
        );

        // when
        Optional<Anomaly> result = detector.apply(temperatureReadings);

        assertFalse(result.isPresent());
    }

    @Test
    void applyWhenTemperatureIsAnomalousThenAnomaly() {
        // given
        List<TemperatureReading> temperatureReadings = Arrays.asList(
            new TemperatureReading(19.1, "room", "thermo", Instant.parse("2023-01-01T00:00:00Z")),
            new TemperatureReading(19.2, "room", "thermo", Instant.parse("2023-01-01T00:00:01Z")),
            new TemperatureReading(19.5, "room", "thermo", Instant.parse("2023-01-01T00:00:02Z")),
            new TemperatureReading(19.3, "room", "thermo", Instant.parse("2023-01-01T00:00:03Z")),
            new TemperatureReading(25.1, "room", "thermo", Instant.parse("2023-01-01T00:00:04Z")),
            new TemperatureReading(18.2, "room", "thermo", Instant.parse("2023-01-01T00:00:05Z")),
            new TemperatureReading(19.1, "room", "thermo", Instant.parse("2023-01-01T00:00:06Z")),
            new TemperatureReading(19.2, "room", "thermo", Instant.parse("2023-01-01T00:00:07Z"))
        );

        // when
        Optional<Anomaly> anomalyOptional = detector.apply(temperatureReadings);

        // then
        assertTrue(anomalyOptional.isPresent());
        assertEquals(new Anomaly(25.1, "room", "thermo", Instant.parse("2023-01-01T00:00:04Z")), anomalyOptional.get());
    }
}
