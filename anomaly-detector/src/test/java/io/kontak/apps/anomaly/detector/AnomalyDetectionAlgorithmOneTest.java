package io.kontak.apps.anomaly.detector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.TemperatureReading;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class AnomalyDetectionAlgorithmOneTest {

    private final AnomalyDetectionAlgorithmOne detector = new AnomalyDetectionAlgorithmOne();

    @Test
    void applyWhenTemperatureReadingsLessThen10ThenNoAnomaly() {
        // given
        List<TemperatureReading> temperatureReadings = Arrays.asList(
            new TemperatureReading(21.2, "room1", "thermo1", Instant.now()),
            new TemperatureReading(20.0, "room1", "thermo1", Instant.now().plusSeconds(1)),
            new TemperatureReading(21.1, "room1", "thermo1", Instant.now().plusSeconds(2)),
            new TemperatureReading(21.2, "room1", "thermo1", Instant.now().plusSeconds(3)),
            new TemperatureReading(22.0, "room1", "thermo1", Instant.now().plusSeconds(4))
        );

        // when
        Optional<Anomaly> anomalyOptional = detector.apply(temperatureReadings);

        // then
        assertTrue(anomalyOptional.isEmpty());
    }

    @Test
    void applyWhenTemperatureIsNormalThenNoAnomaly() {
        // given
        List<TemperatureReading> temperatureReadings = Arrays.asList(
                new TemperatureReading(21.2, "room1", "thermo1", Instant.now()),
                new TemperatureReading(20.0, "room1", "thermo1", Instant.now().plusSeconds(1)),
                new TemperatureReading(21.1, "room1", "thermo1", Instant.now().plusSeconds(2)),
                new TemperatureReading(21.2, "room1", "thermo1", Instant.now().plusSeconds(3)),
                new TemperatureReading(22.0, "room1", "thermo1", Instant.now().plusSeconds(4)),
                new TemperatureReading(21.2, "room1", "thermo1", Instant.now().plusSeconds(5)),
                new TemperatureReading(23.3, "room1", "thermo1", Instant.now().plusSeconds(6)),
                new TemperatureReading(21.5, "room1", "thermo1", Instant.now().plusSeconds(7)),
                new TemperatureReading(21.8, "room1", "thermo1", Instant.now().plusSeconds(8)),
                new TemperatureReading(20.9, "room1", "thermo1", Instant.now().plusSeconds(9))
        );

        // when
        Optional<Anomaly> anomalyOptional = detector.apply(temperatureReadings);

        // then
        assertTrue(anomalyOptional.isEmpty());
    }

    @Test
    void applyWhenTemperatureIsAnomalousThenAnomaly() {
        // given
        List<TemperatureReading> temperatureReadings = Arrays.asList(
            new TemperatureReading(20.3, "room1", "thermo1", Instant.now()),
            new TemperatureReading(19.1, "room1", "thermo1", Instant.now().plusSeconds(1)),
            new TemperatureReading(20.1, "room1", "thermo1", Instant.now().plusSeconds(2)),
            new TemperatureReading(19.2, "room1", "thermo1", Instant.now().plusSeconds(3)),
            new TemperatureReading(20.1, "room1", "thermo1", Instant.now().plusSeconds(4)),
            new TemperatureReading(18.1, "room1", "thermo1", Instant.now().plusSeconds(5)),
            new TemperatureReading(19.4, "room1", "thermo1", Instant.now().plusSeconds(6)),
            new TemperatureReading(20.1, "room1", "thermo1", Instant.now().plusSeconds(7)),
            new TemperatureReading(27.1, "room1", "thermo1", Instant.now().plusSeconds(8)),
            new TemperatureReading(23.1, "room1", "thermo1", Instant.now().plusSeconds(9))
        );

        // when
        Optional<Anomaly> anomalyOptional = detector.apply(temperatureReadings);

        // then
        assertTrue(anomalyOptional.isPresent());
        Anomaly anomaly = anomalyOptional.get();
        assertEquals(27.1, anomaly.temperature());
        assertEquals("room1", anomaly.roomId());
        assertEquals("thermo1", anomaly.thermometerId());
    }
}
