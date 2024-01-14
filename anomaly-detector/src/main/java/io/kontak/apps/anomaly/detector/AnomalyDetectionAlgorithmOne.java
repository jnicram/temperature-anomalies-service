package io.kontak.apps.anomaly.detector;

import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.TemperatureReading;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "anomaly-detector.type", havingValue = "algorithm-one")
class AnomalyDetectionAlgorithmOne implements AnomalyDetector {

    private static final int WINDOW_SIZE = 10;
    private static final double THRESHOLD = 5.0;

    private final LinkedList<TemperatureReading> window = new LinkedList<>();
    private final PriorityQueue<TemperatureReading> maxTemperatureHeap = new PriorityQueue<>((a, b) ->
        Double.compare(b.temperature(), a.temperature()));

    @Override
    public Optional<Anomaly> apply(List<TemperatureReading> temperatureReadings) {
        for (TemperatureReading reading : temperatureReadings) {
            Optional<Anomaly> anomaly = addTemperatureAndDetectAnomaly(reading);
            if (anomaly.isPresent()) {
                return anomaly;
            }
        }
        return Optional.empty();
    }

    private Optional<Anomaly> addTemperatureAndDetectAnomaly(TemperatureReading reading) {
        manageWindowSize();

        window.addLast(reading);
        maxTemperatureHeap.add(reading);

        if (window.size() == WINDOW_SIZE) {
            TemperatureReading max = maxTemperatureHeap.peek();
            double maxTemperature = max.temperature();
            double avg = calculateAverageTemperature(maxTemperature);

            if (maxTemperature - avg >= THRESHOLD) {
                return Optional.of(createAnomaly(max));
            }
        }
        return Optional.empty();
    }

    private void manageWindowSize() {
        if (window.size() == WINDOW_SIZE) {
            TemperatureReading removed = window.removeFirst();
            maxTemperatureHeap.remove(removed);
        }
    }

    private double calculateAverageTemperature(double maxTemperature){
        double sum = window.stream()
            .map(TemperatureReading::temperature)
            .mapToDouble(Double::doubleValue)
            .sum();
        return (sum - maxTemperature) / (window.size() - 1);
    }

    private Anomaly createAnomaly(TemperatureReading reading) {
        return new Anomaly(reading.temperature(), reading.roomId(), reading.thermometerId(), reading.timestamp());
    }
}
