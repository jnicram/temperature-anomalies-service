package io.kontak.apps.temperature.generator;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "temperature-generator.type", havingValue = "celsius")
class SimpleCelsiusTemperatureGenerator extends SimpleTemperatureGenerator {

    @Override
    double convertToUnit(double celsius) {
        return celsius;
    }
}
