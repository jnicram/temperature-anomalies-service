package io.kontak.apps.temperature.generator;

import io.kontak.apps.event.TemperatureReading;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

abstract class SimpleTemperatureGenerator implements TemperatureGenerator {

  /**
   * Configuration variable that stores room and thermometer mappings.
   * The configuration is a {@code Map} where the key represents the room ID and the value is a list of thermometer IDs
   * associated with that room.
   */
  private static final Map<String, List<String>> CONFIGURATION = Map.of(
      "room-" + UUID.randomUUID(), List.of("thermometer-" + UUID.randomUUID()),
      "room-" + UUID.randomUUID(), List.of("thermometer-" + UUID.randomUUID(), "thermometer-" + UUID.randomUUID()),
      "room-" + UUID.randomUUID(), List.of("thermometer-" + UUID.randomUUID(), "thermometer-" + UUID.randomUUID(),
          "thermometer-" + UUID.randomUUID())
  );

  private static final double MIN_TEMP = 18.0;
  private static final double MAX_TEMP = 28.0;

  private final Random random = new Random();

  @Override
  public List<TemperatureReading> generate() {
    return List.of(generateSingleReading());
  }

  private TemperatureReading generateSingleReading() {
    Entry<String, List<String>> roomConfiguration = getRandomConfiguration();
    String roomId = roomConfiguration.getKey();
    String thermometerId = getRandomRoomThermometerId(roomConfiguration.getValue());

    return new TemperatureReading(
        convertToUnit(getRandomTemp()),
        roomId,
        thermometerId,
        Instant.now()
    );
  }

  private double getRandomTemp(){
    return MIN_TEMP + (MAX_TEMP - MIN_TEMP) * random.nextDouble();
  }

  private Map.Entry<String, List<String>> getRandomConfiguration() {
    var entries = new ArrayList<>(CONFIGURATION.entrySet());
    return entries.get(random.nextInt(entries.size()));
  }

  private String getRandomRoomThermometerId(List<String> list) {
    return list.get(random.nextInt(list.size()));
  }

  abstract double convertToUnit(double celsius);
}
