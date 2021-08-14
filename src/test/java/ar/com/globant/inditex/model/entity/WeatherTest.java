package ar.com.globant.inditex.model.entity;

import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WeatherTest {

    private Validator validator;
    private Weather weather;

    @BeforeEach
    public void beforeEach() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        LocalDate localDate = LocalDate.of(2000, 3, 10);
        Location location = new Location();
        location.setCity("Rosario");
        location.setState("Santa Fe");
        location.setLatitude(BigDecimal.valueOf(32.95));
        location.setLongitude(BigDecimal.valueOf(60.69));
        this.weather = new Weather();
        weather.setLocation(location);
        weather.setDate(localDate);
        weather.setId(1L);
        List<Temperature> tem = new ArrayList<>(24);
        for (var i = 0; i < 24; i++) {
            var t = new Temperature(1L, Float.valueOf(i));
            t.setId(Long.valueOf(i));
            tem.add(t);
        }
        weather.setTemperatures(tem);
    }

    @Test
    void isValid() {
        Set<ConstraintViolation<Weather>> constraints = validator.validate(weather);
        assertThat(constraints).isEmpty();
    }

    @Test
    void mustHaveDate() {
        weather.setDate(null);
        Set<ConstraintViolation<Weather>> constraints = validator.validate(weather);
        assertThat(constraints).isNotEmpty();
    }

    @Test
    void musHaveLocation() {
        weather.setLocation(null);
        Set<ConstraintViolation<Weather>> constraints = validator.validate(weather);
        assertThat(constraints).isNotEmpty();
    }

    @Test
    void mustHaveId() {
        weather.setId(null);
        Set<ConstraintViolation<Weather>> constraints = validator.validate(weather);
        assertThat(constraints).isNotEmpty();
    }

    @Test
    void mustHaveTemperatures() {
        weather.setTemperatures(null);
        Set<ConstraintViolation<Weather>> constraints = validator.validate(weather);
        assertThat(constraints).isNotEmpty();
    }

    @Test
    void have24Temperatures() {
        List<Temperature> t = weather.getTemperatures();
        List<Temperature> t2 = new ArrayList<>(t);
        t2.remove(0);
        weather.setTemperatures(t2);
        Set<ConstraintViolation<Weather>> constraints = validator.validate(weather);
        assertThat(constraints).isNotEmpty();
    }
}
