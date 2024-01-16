package io.kontak.apps.temperature.generator;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "temperature-generator.type", havingValue = "fahrenheit")
class SimpleFahrenheitTemperatureGenerator extends SimpleTemperatureGenerator {

    @Override
    double convertToUnit(double celsius) {
        return (celsius * 9/5) + 32;
    }
}
