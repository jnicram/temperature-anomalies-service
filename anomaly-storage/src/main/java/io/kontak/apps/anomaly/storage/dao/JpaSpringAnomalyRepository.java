package io.kontak.apps.anomaly.storage.dao;

import java.time.Instant;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

interface JpaSpringAnomalyRepository extends MongoRepository<AnomalyEntity, String> {

  Optional<AnomalyEntity> findByRoomIdAndThermometerIdAndTimestamp(String roomId, String thermId, Instant timestamp);
}
