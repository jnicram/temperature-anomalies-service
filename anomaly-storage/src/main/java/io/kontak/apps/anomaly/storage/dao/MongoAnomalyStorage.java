package io.kontak.apps.anomaly.storage.dao;

import io.kontak.apps.anomaly.storage.AnomalyStorage;
import io.kontak.apps.event.Anomaly;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class MongoAnomalyStorage implements AnomalyStorage {

    private final JpaAnomalyRepository anomalyRepository;

    @Override
    public void accept(Anomaly anomaly) {
        anomalyRepository.insert(anomaly);
    }
}
