package co.com.certicamara.RCVBackEnd.rest.controller;

import java.awt.Point;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.com.certicamara.RCVBackEnd.exceptions.BusinessException;
import co.com.certicamara.RCVBackEnd.model.Map;
import co.com.certicamara.RCVBackEnd.service.RCVehicleService;

@RestController
@RequestMapping("/v1")
public class RCVehicleRESTController {

	@Value("${INVALID_COMMANDS}")
	private String invalidCommnands;

	@Autowired
	private RCVehicleService rcVehicleService;

	@RequestMapping(value = "/Map", method = RequestMethod.POST)
	public ResponseEntity<String> configMap(@RequestBody Point mapPoint) throws BusinessException {
		try {
			rcVehicleService.createMap(mapPoint.y, mapPoint.x);
			Map map = rcVehicleService.getMap();
			return new ResponseEntity<String>(map.toString(), HttpStatus.OK);

		} catch (BusinessException be) {
			throw be;
		} catch (Exception ex) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/Map", method = RequestMethod.GET)
	public ResponseEntity<Map> getMap() {
		try {
			Map map = rcVehicleService.getMap();
			return new ResponseEntity<Map>(map, HttpStatus.OK);

		} catch (Exception ex) {
			return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/moveVehicle", method = RequestMethod.POST)
	public ResponseEntity<String> moveVehicle(@RequestBody String commands)
			throws BusinessException {
		try {

			if (!commands.matches("([0-9]*,[NSEO]|;)+")) {
				throw new BusinessException(invalidCommnands);
			} else {
				String[] commandsArray = commands.split(";");

				rcVehicleService.moveVehicle(commandsArray);
				String position = rcVehicleService.getPosition();

				return new ResponseEntity<String>(position, HttpStatus.OK);
			}
		} catch (BusinessException be) {
			throw be;
		} catch (Exception ex) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/getPosition", method = RequestMethod.GET)
	public ResponseEntity<String> getPosition() {
		try {
			String position = rcVehicleService.getPosition();
			return new ResponseEntity<String>(position, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/restartPosition", method = RequestMethod.DELETE)
	public ResponseEntity<String> restartVehiclePosition() {
		try {
			rcVehicleService.restartVehiclePosition();
			String position = rcVehicleService.getPosition();
			return new ResponseEntity<String>(position, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<String> exceptionHandler(BusinessException ex) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
	}

}
