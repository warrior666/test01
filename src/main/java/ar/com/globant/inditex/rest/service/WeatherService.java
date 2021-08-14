package ar.com.globant.inditex.rest.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import ar.com.globant.inditex.model.entity.Weather;
import ar.com.globant.inditex.model.factory.WeatherFactory;
import ar.com.globant.inditex.model.repository.WeatherRepository;
import ar.com.globant.inditex.rest.dto.WeatherDto;

@Service
public class WeatherService {

    private WeatherRepository weatherRepository;

    @Autowired
    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public Weather create(WeatherDto weather) {
        Optional<Weather> exists = weatherRepository.findById(weather.getId());
        if (exists.isPresent()) {
            // Ya existe por ID
            throw new HttpClientErrorException(HttpStatus.CONFLICT);
        }
        return weatherRepository.save(WeatherFactory.createWeatherFromDto(weather));
    }

    public List<WeatherDto> getAllElements() {
        return StreamSupport.stream(weatherRepository.findAllByOrderByIdAsc().spliterator(), false)
            .map(WeatherFactory::createWeatherDtoFromModel)
            .collect(Collectors.toUnmodifiableList());
    }

    public List<WeatherDto> getElementsByDate(LocalDate date) {
        return StreamSupport.stream(weatherRepository.findAllByDateOrderByIdAsc(date).spliterator(), false)
            .map(WeatherFactory::createWeatherDtoFromModel)
            .collect(Collectors.toUnmodifiableList());
    }

    public void deleteAll() {
        this.weatherRepository.deleteAll();
    }
}
