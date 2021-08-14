package ar.com.globant.inditex.rest.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ar.com.globant.inditex.application.configuration.SwaggerConfig;
import ar.com.globant.inditex.rest.service.WeatherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/delete")
@Api(tags = { SwaggerConfig.WEATHER_TAG })
public class DeleteController {

    private static final Log LOGGER = LogFactory.getLog(DeleteController.class);
    private WeatherService weatherService;

    @Autowired
    public DeleteController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @DeleteMapping()
    @ApiOperation(value = "Remove all items")
    @ApiResponses(value = @ApiResponse(code = 200, message = "All items are deleted"))
    public void delete() {
        LOGGER.info("All items will be removed");
        weatherService.deleteAll();
    }
}
