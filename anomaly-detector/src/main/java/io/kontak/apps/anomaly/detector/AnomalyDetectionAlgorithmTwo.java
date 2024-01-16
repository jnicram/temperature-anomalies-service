package io.kontak.apps.anomaly.detector;

import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.TemperatureReading;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "temperature-detector.type", havingValue = "algorithm-two")
class AnomalyDetectionAlgorithmTwo implements AnomalyDetector {

    private static final int WINDOW_SIZE = 10;
    private static final double THRESHOLD = 5.0;

    private final LinkedList<TemperatureReading> window = new LinkedList<>();

    @Override
    public Optional<Anomaly> apply(List<TemperatureReading> temperatureReadings) {
        for (TemperatureReading reading : temperatureReadings) {
            if (!window.isEmpty() && isOutsideWindow(reading, window.getFirst())) {
                window.removeFirst();
            }

            double currentAvg = calculateAverageTemperature(window);
            window.addLast(reading);

            if (isAnomaly(reading, currentAvg)) {
                return Optional.of(createAnomaly(reading));
            }
        }
        return Optional.empty();
    }

    private boolean isOutsideWindow(TemperatureReading current, TemperatureReading first) {
        return Math.abs(Duration.between(current.timestamp(), first.timestamp()).getSeconds()) > WINDOW_SIZE;
    }

    private double calculateAverageTemperature(LinkedList<TemperatureReading> window) {
        return window.stream().mapToDouble(TemperatureReading::temperature).average().orElse(0);
    }

    private boolean isAnomaly(TemperatureReading reading, double currentAvg) {
        return currentAvg != 0 && Math.abs(reading.temperature() - currentAvg) >= THRESHOLD;
    }

    private Anomaly createAnomaly(TemperatureReading reading) {
        return new Anomaly(reading.temperature(), reading.roomId(), reading.thermometerId(), reading.timestamp());
    }
}
