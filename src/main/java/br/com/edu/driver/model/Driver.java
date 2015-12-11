package br.com.edu.driver.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "drivers")
public class Driver implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@NotNull
	@Column(name="driver_available")
	private Boolean driverAvailable;
	
	@Column(name="latitude")
	private Double latitude;
	

	@Column(name="longitude")
	private Double longitude;
	
	@NotNull
	@Column(name="name")
	private String name;
	
	@NotNull
	@Column(name="plate")
	private String plate;
	
	public Driver() {
	}

	public Driver(Long id, Boolean driverAvailable, Double latitude,
			Double longitude) {
		super();
		this.id = id;
		this.driverAvailable = driverAvailable;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Driver(String name, String plate) {
		super();
		this.name = name;
		this.plate = plate;
	}

	@JsonProperty("driverId")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getDriverAvailable() {
		return driverAvailable;
	}

	public void setDriverAvailable(Boolean driverAvailable) {
		this.driverAvailable = driverAvailable;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	
}