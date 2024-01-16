package io.kontak.apps.anomaly.storage;

import io.kontak.apps.event.Anomaly;
import java.util.function.Consumer;

public interface AnomalyStorage extends Consumer<Anomaly> {

}
