package ar.com.globant.inditex.model.entity;

import java.math.BigDecimal;
import javax.persistence.Embeddable;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@Embeddable
@Valid
public class Location {

    @NotNull
    @DecimalMax(value = "90")
    @DecimalMin(value = "-90")
    @Digits(integer = 2, fraction = 2)
    private BigDecimal latitude;
    @NotNull
    @DecimalMax(value = "180")
    @DecimalMin(value = "-180")
    @Digits(integer = 3, fraction = 2)
    private BigDecimal longitude;
    @NotNull
    private String city;
    @NotNull
    private String state;
}
