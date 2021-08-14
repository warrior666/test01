package ar.com.globant.inditex.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import ar.com.globant.inditex.model.entity.Location;
import ar.com.globant.inditex.model.factory.WeatherFactory;
import ar.com.globant.inditex.model.repository.WeatherRepository;
import ar.com.globant.inditex.rest.dto.WeatherDto;
import ar.com.globant.inditex.rest.service.WeatherService;

@AutoConfigureMockMvc
@SpringBootTest
class WeatherControllerTest {

    private WeatherController controller;
    private WeatherService weatherService;
    private WeatherDto dto;

    @BeforeEach
    public void setUp() {
        weatherService = mock(WeatherService.class);
        controller = new WeatherController(weatherService);
        this.dto = new WeatherDto();
        dto.setId(1L);
        LocalDate localDate = LocalDate.of(2000, 3, 10);
        Location location = new Location();
        location.setCity("Rosario");
        location.setState("Santa Fe");
        location.setLatitude(BigDecimal.valueOf(32.95));
        location.setLongitude(BigDecimal.valueOf(60.69));
        dto.setLocation(location);
        dto.setDate(localDate);
        List<Long> tem = new ArrayList<>(24);
        for (var i = 0; i < 24; i++) {
            tem.add(Long.valueOf(i));
        }
        dto.setTemperatures(tem);
    }

    @Test
    void createResponse201() {
        ResponseEntity<WeatherDto> response = controller.create(dto);
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void repitedIdThrowError() {
        WeatherRepository weatherRepository = mock(WeatherRepository.class);
        when(weatherRepository.findById(dto.getId())).thenReturn(Optional.of(WeatherFactory.createWeatherFromDto(dto)));
        WeatherService weatherServiceLocal = new WeatherService(weatherRepository);
        WeatherController controllerLocal = new WeatherController(weatherServiceLocal);
        assertThrows(HttpClientErrorException.class, () -> controllerLocal.create(dto));
    }

    @Test
    void getWithoutDateDontThrow() {
        when(this.weatherService.getAllElements()).thenReturn(Collections.emptyList());
        ResponseEntity<List<WeatherDto>> response = controller.list(null);
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getEmptyListWithDateThrowException() {
        when(this.weatherService.getElementsByDate(any())).thenReturn(Collections.emptyList());
        ResponseEntity<List<WeatherDto>> response = controller.list(LocalDate.now());
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getNonEmptyListWithDateThrowException() {
        when(this.weatherService.getElementsByDate(any())).thenReturn(Collections.singletonList(dto));
        ResponseEntity<List<WeatherDto>> response = controller.list(LocalDate.now());
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
