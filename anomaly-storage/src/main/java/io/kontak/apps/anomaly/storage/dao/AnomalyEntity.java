package io.kontak.apps.anomaly.storage.dao;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@Document(collection = "anomaly")
public class AnomalyEntity {

  @Id
  private String id;

  private double temperature;

  private String roomId;

  private String thermometerId;

  private Instant timestamp;
}
