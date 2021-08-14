package ar.com.globant.inditex.model.entity;

import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LocationTest {

    private Location location;
    private Validator validator;

    @BeforeEach
    public void beforeEach() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.location = new Location();
        location.setCity("Rosario");
        location.setState("Santa Fe");
        location.setLatitude(BigDecimal.valueOf(32.95));
        location.setLongitude(BigDecimal.valueOf(60.69));
    }

    @Test
    void rosarioIsValid() {
        Set<ConstraintViolation<Location>> constraints = validator.validate(location);
        assertThat(constraints).isEmpty();
    }

    @Test
    void locationWithoutCityIsInvalid() {
        location.setCity(null);
        Set<ConstraintViolation<Location>> constraints = validator.validate(location);
        assertThat(constraints).isNotEmpty();
    }

    @Test
    void locationWithoutStateIsInvalid() {
        location.setState(null);
        Set<ConstraintViolation<Location>> constraints = validator.validate(location);
        assertThat(constraints).isNotEmpty();
    }

    @Test
    void locationWithoutLatitudIsInvalid() {
        location.setLatitude(null);
        Set<ConstraintViolation<Location>> constraints = validator.validate(location);
        assertThat(constraints).isNotEmpty();
    }

    @Test
    void locationWithoutLongitudeIsInvalid() {
        location.setLongitude(null);
        Set<ConstraintViolation<Location>> constraints = validator.validate(location);
        assertThat(constraints).isNotEmpty();
    }

    @Test
    void minLatitud() {
        location.setLatitude(BigDecimal.valueOf(-91));
        Set<ConstraintViolation<Location>> constraints = validator.validate(location);
        assertThat(constraints).isNotEmpty();
    }

    @Test
    void maxLatitud() {
        location.setLatitude(BigDecimal.valueOf(91));
        Set<ConstraintViolation<Location>> constraints = validator.validate(location);
        assertThat(constraints).isNotEmpty();
    }

    @Test
    void maxLongitude() {
        location.setLongitude(BigDecimal.valueOf(181));
        Set<ConstraintViolation<Location>> constraints = validator.validate(location);
        assertThat(constraints).isNotEmpty();
    }

    @Test
    void minLongitude() {
        location.setLongitude(BigDecimal.valueOf(-181));
        Set<ConstraintViolation<Location>> constraints = validator.validate(location);
        assertThat(constraints).isNotEmpty();
    }
}
