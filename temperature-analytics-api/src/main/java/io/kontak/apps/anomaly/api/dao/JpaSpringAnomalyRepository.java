package io.kontak.apps.anomaly.api.dao;

import io.kontak.apps.anomaly.storage.dao.AnomalyEntity;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

interface JpaSpringAnomalyRepository extends MongoRepository<AnomalyEntity, String> {

  List<AnomalyEntity> findByThermometerId(String thermometerId);

  List<AnomalyEntity> findByRoomId(String roomId);
}
