package io.kontak.apps.anomaly.storage.config;

import io.kontak.apps.anomaly.storage.AnomalyStorage;
import io.kontak.apps.event.Anomaly;
import java.util.function.Consumer;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public Consumer<KStream<String, Anomaly>> anomaliesConsumer(AnomalyStorage anomalyStorage) {
        return input -> input.foreach((key, value) -> anomalyStorage.accept(value));
    }
}
