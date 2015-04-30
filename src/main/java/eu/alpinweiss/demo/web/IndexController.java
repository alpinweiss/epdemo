package eu.alpinweiss.demo.web;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import eu.alpinweiss.demo.domain.Planet;
import eu.alpinweiss.demo.domain.PlanetRepository;
import eu.alpinweiss.demo.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class IndexController {

	public static final String API_PLANET_LIST = "/api/planet/list";
	public static final String API_FIND_PLANET = "/api/planet/{planetPk}";
	public static final String API_PLANET_ADD = "/api/planet/persist";
	public static final String API_PLANET_DELETE = "/api/planet/{planetPk}";

	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private PlanetRepository planetRepository;
	@Autowired
	private MessageService messageService;

	@RequestMapping(API_PLANET_LIST)
	public List<Planet> index() {
		return planetRepository.findAll();
	}

	@ResponseBody
	@RequestMapping(API_FIND_PLANET)
	public Planet findPlanetById(@PathVariable("planetPk") Long planetPk) {
		return planetRepository.findOne(planetPk);
	}

	@RequestMapping(value = API_PLANET_ADD, method = RequestMethod.POST)
	public void addPlanet(@RequestBody @Valid Planet planet) {
		planetRepository.save(planet);
	}

	@RequestMapping(value = API_PLANET_DELETE, method = RequestMethod.DELETE)
	public void deletePlanet(@PathVariable("planetPk") Long planetPk) {
		planetRepository.delete(planetPk);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Map<String, String> handleValidationError(MethodArgumentNotValidException ex) {
		return messageService.createUserReadableMessage(ex);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Map<String, String> handleJSONTransformationError(HttpMessageNotReadableException ex) {
		return messageService.createUserReadableMessage(ex);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public String handleUnexpectedException(Exception ex) {
		logger.error("Error during process request", ex);
		return ex.getMessage();
	}

}
