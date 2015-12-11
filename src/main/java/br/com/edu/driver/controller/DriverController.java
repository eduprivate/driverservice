package br.com.edu.driver.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.edu.driver.exception.PreconditionException;
import br.com.edu.driver.model.Driver;
import br.com.edu.driver.service.DriverService;

@RestController
public class DriverController {
	
	private DriverService driverService;
	
	private Logger logger = LogManager.getLogger(DriverController.class);
	
	@RequestMapping(value="/drivers", method = RequestMethod.POST )
    public ResponseEntity<String> createDriver(@RequestBody Driver driver) {
    	try {
			driverService.saveDriver(driver);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (NumberFormatException e) {
			logger.error("An error occurred when save driver. ", e);
		} catch (PreconditionException e) {
			logger.error("An error occurred when save driver. ", e);
		}
    	return new ResponseEntity<String>(HttpStatus.PRECONDITION_FAILED); 
    }

	@RequestMapping(value="/drivers/status", method = RequestMethod.POST )
    public ResponseEntity<String> saveDriverPosition(@RequestBody Driver driver) {
    	try {
    		Driver driverFound = driverService.findById(driver.getId());
    		if(driverFound != null){
    			driverFound.setDriverAvailable(driver.getDriverAvailable());
    			driverFound.setLatitude(driver.getLatitude());
    			driverFound.setLongitude(driver.getLongitude());
    			driverService.saveDriver(driverFound);
    		}
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (NumberFormatException e) {
			logger.error("An error occurred when updating position. ", e);
		} catch (PreconditionException e) {
			logger.error("An error occurred when updating position. ", e);
		}
    	return new ResponseEntity<String>(HttpStatus.PRECONDITION_FAILED); 
    }
	
	@RequestMapping(value="/drivers/{id}/status", method = RequestMethod.GET )
    public ResponseEntity<Driver> getDriverPosition(@PathVariable("id") Long id) {
    	try {
    		Driver driverFound = driverService.findById(id);
    		if(driverFound != null)
    			return new ResponseEntity<Driver>(driverFound, HttpStatus.ACCEPTED);
		} catch (NumberFormatException e) {
			logger.error("An error occurred when updating position. ", e);
		}
    	return new ResponseEntity<Driver>(HttpStatus.PRECONDITION_FAILED); 
    }
	
	@RequestMapping(value="/drivers/inArea", method = RequestMethod.GET )
    public List<Driver> inArea(@PathParam("sw") String sw, @PathParam("ne") String ne) {
		List<Driver> drivers = null;
    	try {
    		drivers = driverService.findDriversInArea(sw, ne);
    		if(drivers != null && !drivers.isEmpty())
    			return drivers;
    		
		} catch (NumberFormatException e) {
			logger.error("An error occurred when updating position. ", e);
		}
    	return drivers; 
    }

	
	@Autowired
	public void setDriverService(DriverService driverService) {
		this.driverService = driverService;
	}
}
