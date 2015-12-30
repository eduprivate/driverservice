package br.com.edu.driver.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.redisson.Redisson;
import org.redisson.RedissonClient;
import org.redisson.core.RBucket;
import org.redisson.core.RMapCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.edu.driver.exception.PreconditionException;
import br.com.edu.driver.model.Driver;
import br.com.edu.driver.repository.DriverRepository;

@Service
public class CachedDriverService {
	
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
		saveOnCache(driver);
		
	}

	private void saveOnCache(Driver driver) {
		try {
			RedissonClient redisson = Redisson.create();
			RMapCache<Long, Driver> map = redisson.getMapCache("driverMap");
			map.put(driver.getId(), driver, 60, TimeUnit.MINUTES);
		} catch (Exception e) {
			System.out.println("Error with cache server...");
		}
	}
	
	private Driver getOnCache(Long id){
		Driver driver = null;
		try {
			RedissonClient redisson = Redisson.create();
			RMapCache<Long, Driver> map = redisson.getMapCache("driverMap");
			driver = map.get(id);
		} catch (Exception e) {
			System.out.println("Error with cache server...");
		}
		return driver;
	}
	
	public Driver findById(Long id) {
		 Driver driver = getOnCache(id);
		 if (driver == null){
			 driver = driverRepository.findById(id);
			 saveOnCache(driver);
		 }
		 return driver;
	}
	
	public List<Driver> findDriversInArea(String sw, String ne) {
		
		String[] swCoordinates = sw.split(",");
		Double swLatitute = new Double(swCoordinates[0]);
		Double swLongitude = new Double(swCoordinates[1]);
		
		String[] neCoordinates = ne.split(",");
		Double neLatitute = new Double(neCoordinates[0]);
		Double neLongitude = new Double(neCoordinates[1]);
		
		List<Driver> drivers = driverRepository.inArea(swLatitute, neLatitute, swLongitude, neLongitude);
		
		Double centroidLatitude = (swLatitute - neLatitute) / 2;
		Double centroidLongitude = (swLongitude - neLongitude) / 2;
		
		Map<Double, Driver> distances = new HashMap<Double, Driver>();
		
		for (Driver driver : drivers) {
			distances.put(calculeDistance(centroidLatitude, centroidLongitude, driver.getLatitude(), driver.getLongitude()), driver);
		}
		Map<Double, Driver> sortedDistance = new TreeMap<Double, Driver>(distances);
		
		List<Driver> sortedDriversList = mountSortedList(sortedDistance);
		
		return sortedDriversList;
	}
	
	private List<Driver> mountSortedList(Map<Double,Driver> map) {
		List<Driver> drivers = new ArrayList<Driver>();
	    Set<Entry<Double, Driver>> s = map.entrySet();
	    Iterator<Entry<Double, Driver>> it = s.iterator();
	    while ( it.hasNext() ) {
	       Map.Entry entry = (Map.Entry) it.next();
	       drivers.add((Driver)entry.getValue());  
	    }
	    return drivers;
	}
	
	private Double calculeDistance(Double centroidLatitude, Double centroidLongitude, Double latitude, Double longitude ) {
		Double a = Math.pow(Math.sin(centroidLatitude),2) + Math.cos(latitude) * Math.cos(centroidLatitude) * Math.pow(Math.sin(centroidLatitude),2);  
        Double distancia = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));  
        return 6378140 * distancia; 
		
	}
	
	@Autowired
	public void setDriverRepository(DriverRepository driverRepository) {
		this.driverRepository = driverRepository;
	}

	
}
