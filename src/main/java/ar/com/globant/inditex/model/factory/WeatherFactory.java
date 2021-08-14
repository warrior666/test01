package ar.com.globant.inditex.model.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import ar.com.globant.inditex.model.entity.Temperature;
import ar.com.globant.inditex.model.entity.Weather;
import ar.com.globant.inditex.rest.dto.WeatherDto;

public class WeatherFactory {

    private WeatherFactory() {
    }

    public static Weather createWeatherFromDto(WeatherDto dto) {
        Weather weather = new Weather();
        weather.setId(dto.getId());
        weather.setDate(dto.getDate());
        weather.setLocation(dto.getLocation());
        List<Long> temperatures = dto.getTemperatures();
        List<Temperature> newTemperatures = new ArrayList<>(dto.getTemperatures().size());
        for (Long value : temperatures) {
            newTemperatures.add(new Temperature(weather.getId(), Float.valueOf(value)));
        }
        weather.setTemperatures(newTemperatures);
        return weather;
    }

    public static WeatherDto createWeatherDtoFromModel(Weather a) {
        WeatherDto dto = new WeatherDto();
        dto.setId(a.getId());
        dto.setDate(a.getDate());
        dto.setLocation(a.getLocation());
        dto.setTemperatures(a.getTemperatures()
            .stream()
            .map(t -> t.getValue().longValue())
            .collect(Collectors.toUnmodifiableList()));
        return dto;
    }
}
