package ar.com.globant.inditex.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "meteorological_data_temperatures")
@Valid
public class Temperature {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
    @JsonIgnore
    @NotNull
    @Column(name = "meteorological_data")
    private Long meteorologicalData;
    @NotNull
    @Column(name = "temperature")
    private Float value;

    public Temperature(Long meteorologicalData, Float value) {
        this.meteorologicalData = meteorologicalData;
        this.value = value;
    }
}
