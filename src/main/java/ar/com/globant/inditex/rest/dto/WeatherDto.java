package ar.com.globant.inditex.rest.dto;

import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;
import ar.com.globant.inditex.model.entity.Location;
import lombok.Data;

@Data
@Valid
public class WeatherDto {

    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    @Valid
    private Location location;
    @Size(min = 24, max = 24)
    @NotEmpty
    private List<Long> temperatures;
}
