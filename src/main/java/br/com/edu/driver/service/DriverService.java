package br.com.edu.driver.service;

import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.edu.driver.exception.PreconditionException;
import br.com.edu.driver.model.Driver;
import br.com.edu.driver.repository.DriverRepository;

@Service
public class DriverService {
	
	private DriverRepository driverRepository;
	
	public void saveDriver(Driver driver) throws PreconditionException {
		
		if (driver.getName() == null || driver.getPlate() == null)
			throw new PreconditionException("Name and Plate is mandatory!");
		if (driver.getLatitude() == null)
			driver.setLatitude(0d);
		if (driver.getLongitude() == null)
			driver.setLongitude(0d);
		if (driver.getDriverAvailable() == null)
			driver.setDriverAvailable(false);
		 driverRepository.save(driver);
	}
	
	public Driver findById(Long id) {
		 return driverRepository.findById(id);
	}
	
	@Autowired
	public void setDriverRepository(DriverRepository driverRepository) {
		this.driverRepository = driverRepository;
	}

	public List<Driver> findDriversInArea(String sw, String ne) {
		
		String[] swCoordinates = sw.split(",");
		Double swLatitute = new Double(swCoordinates[0]);
		Double swLongitude = new Double(swCoordinates[1]);
		
		String[] neCoordinates = ne.split(",");
		Double neLatitute = new Double(neCoordinates[0]);
		Double neLongitude = new Double(neCoordinates[1]);
		
		List<Driver> drivers = driverRepository.inArea(swLatitute, neLatitute, swLongitude, neLongitude);
		
		return drivers;
	}
	
}
