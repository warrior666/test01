package ar.com.globant.inditex.model.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ar.com.globant.inditex.model.entity.Weather;

@Repository
public interface WeatherRepository extends CrudRepository<Weather, Long> {

    
    List<Weather> findAllByOrderByIdAsc();
    
    List<Weather> findAllByDateOrderByIdAsc(LocalDate date);
}
