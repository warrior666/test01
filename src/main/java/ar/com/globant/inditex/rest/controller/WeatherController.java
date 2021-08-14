package ar.com.globant.inditex.rest.controller;

import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ar.com.globant.inditex.application.configuration.SwaggerConfig;
import ar.com.globant.inditex.model.entity.Weather;
import ar.com.globant.inditex.rest.dto.WeatherDto;
import ar.com.globant.inditex.rest.service.WeatherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/weather")
@Api(tags = { SwaggerConfig.WEATHER_TAG })
public class WeatherController {

    private static final Log LOGGER = LogFactory.getLog(WeatherController.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping
    @ApiOperation(value = "Allows you to create a new record ")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Success"),
        @ApiResponse(code = 400, message = "Error on data"),
        @ApiResponse(code = 409, message = "Element id is already inserted") })
    public ResponseEntity<WeatherDto> create(@Validated @Valid @NotBlank @RequestBody WeatherDto weather) {
        Weather newObject = weatherService.create(weather);
        LOGGER.info("Object created: " + toLogData(newObject));
        return new ResponseEntity<>(weather, HttpStatus.CREATED);
    }

    private String toLogData(Object object) {
        if (object == null) {
            return "null";
        }
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return object.toString();
        }
    }

    @GetMapping()
    @ApiOperation(value = "The list of items sorted by id in ascending order.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The list of records, by date if is provided"),
        @ApiResponse(code = 404, message = "If there are no records for the date entered"), })
    public ResponseEntity<List<WeatherDto>> list(
        @ApiParam(name = "date", required = false, example = "2017-11-15") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        if (date == null) {
            return new ResponseEntity<>(weatherService.getAllElements(), HttpStatus.OK);
        } else {
            LOGGER.info("Searching: " + toLogData(date));
            List<WeatherDto> elementsByDate = weatherService.getElementsByDate(date);
            if (elementsByDate.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(elementsByDate, HttpStatus.OK);
        }
    }
}
