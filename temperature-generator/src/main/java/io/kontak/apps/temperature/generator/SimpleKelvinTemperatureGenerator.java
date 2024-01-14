package io.kontak.apps.temperature.generator;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "temperature-generator.type", havingValue = "kelvin")
class SimpleKelvinTemperatureGenerator extends SimpleTemperatureGenerator {

    @Override
    double convertToUnit(double celsius) {
        return celsius + 273.15;
    }
}
