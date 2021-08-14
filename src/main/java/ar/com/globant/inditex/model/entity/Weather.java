package ar.com.globant.inditex.model.entity;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@Table(name = "meteorological_data")
@Entity
@Valid
public class Weather {

    @Id
    @NotNull
    private Long id;
    @Column(name="local_date")
    @NotNull
    private LocalDate date;
    @NotNull
    @Embedded
    private Location location;
    @Size(min=24, max=24)
    @NotNull
    @NotEmpty
    @OneToMany(mappedBy = "meteorologicalData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Temperature> temperatures;
}
