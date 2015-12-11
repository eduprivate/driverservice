package br.com.edu.driver.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import br.com.edu.Application;
import br.com.edu.driver.model.Driver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebAppConfiguration
@IntegrationTest
public class DriverControllerTest {

	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private RestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void saveDriverTest() throws JsonProcessingException {
		// Give
		Map<String, Object> requestCreateDriver = new HashMap<String, Object>();
		requestCreateDriver.put("name", "John Doe");
		requestCreateDriver.put("plate", "TXI-6675");

		// When
		@SuppressWarnings({ "unchecked" })
		ResponseEntity<Driver> responseCreate = restTemplate.postForEntity(
				"http://localhost:8080/drivers/", requestCreateDriver,
				Driver.class, Collections.EMPTY_MAP);

		// Then
		assertNotNull(responseCreate);
		assertEquals(HttpStatus.OK, responseCreate.getStatusCode());

	}

	@Test()
	public void saveDriverExceptionTest() throws JsonProcessingException {
		// Give
		Map<String, Object> requestCreateDriver = new HashMap<String, Object>();
		requestCreateDriver.put("name", "John Doe");

		// When
		@SuppressWarnings({ "unchecked" })
		ResponseEntity<Driver> responseCreate = restTemplate.postForEntity(
				"http://localhost:8080/drivers/", requestCreateDriver,
				Driver.class, Collections.EMPTY_MAP);

		// Then
		assertNotNull(responseCreate);
		assertEquals(HttpStatus.PRECONDITION_FAILED,
				responseCreate.getStatusCode());

	}

	@Test
	public void savePositionTest() throws JsonProcessingException {
		// Give
		Map<String, Object> requestCreateDriver = new HashMap<String, Object>();
		requestCreateDriver.put("name", "John Doe");
		requestCreateDriver.put("plate", "TXI-9988");

		Map<String, Object> requestBody = new HashMap<String, Object>();
		requestBody.put("driverId", "1");
		requestBody.put("latitude", "-23.60810717");
		requestBody.put("longitude", "-46.67500346");
		requestBody.put("driverAvailable", "true");

		// When
		@SuppressWarnings({ "unused", "unchecked" })
		ResponseEntity<Driver> responseCreate = restTemplate.postForEntity(
				"http://localhost:8080/drivers/", requestCreateDriver,
				Driver.class, Collections.EMPTY_MAP);

		@SuppressWarnings({ "unchecked" })
		ResponseEntity<String> response = restTemplate.postForEntity(
				"http://localhost:8080/drivers/status", requestBody,
				String.class, Collections.EMPTY_MAP);

		// Then
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	public void getDriverStatusTest() throws JsonProcessingException {
		// Give
		Map<String, Object> requestCreateDriver = new HashMap<String, Object>();
		requestCreateDriver.put("name", "John Doe");
		requestCreateDriver.put("plate", "TXI-9988");

		Map<String, Object> requestBody = new HashMap<String, Object>();
		requestBody.put("driverId", "1");
		requestBody.put("latitude", "-23.60810717");
		requestBody.put("longitude", "-46.67500346");
		requestBody.put("driverAvailable", "true");

		// When
		@SuppressWarnings({ "unused", "unchecked" })
		ResponseEntity<Driver> responseCreate = restTemplate.postForEntity(
				"http://localhost:8080/drivers/", requestCreateDriver,
				Driver.class, Collections.EMPTY_MAP);

		@SuppressWarnings({ "unused", "unchecked" })
		ResponseEntity<String> response = restTemplate.postForEntity(
				"http://localhost:8080/drivers/status", requestBody,
				String.class, Collections.EMPTY_MAP);

		@SuppressWarnings({ "unchecked" })
		ResponseEntity<Driver> responseStatus = restTemplate.getForEntity(
				"http://localhost:8080/drivers/" + requestBody.get("driverId")
						+ "/status", Driver.class, Collections.EMPTY_MAP);

		// Then
		assertNotNull(responseStatus);
		assertEquals(HttpStatus.ACCEPTED, responseStatus.getStatusCode());

	}

	@Test
	public void getDriverInAreaTest() throws JsonProcessingException {
		// Give
		Map<String, Object> requestCreateDriver1 = new HashMap<String, Object>();
		requestCreateDriver1.put("name", "John Doe");
		requestCreateDriver1.put("plate", "TXI-9988");

		Map<String, Object> requestCreateDriver2 = new HashMap<String, Object>();
		requestCreateDriver2.put("name", "Mary Doe");
		requestCreateDriver2.put("plate", "TXA-9987");

		Map<String, Object> requestBody1 = new HashMap<String, Object>();
		requestBody1.put("driverId", "1");
		requestBody1.put("latitude", "-23.60810717");
		requestBody1.put("longitude", "-46.67500346");
		requestBody1.put("driverAvailable", "true");

		Map<String, Object> requestBody2 = new HashMap<String, Object>();
		requestBody2.put("driverId", "1");
		requestBody2.put("latitude", "-23.59065045044675");
		requestBody2.put("longitude", "-46.68837101634931");
		requestBody2.put("driverAvailable", "true");

		// When
		@SuppressWarnings({ "unused", "unchecked" })
		ResponseEntity<Driver> responseCreate1 = restTemplate.postForEntity(
				"http://localhost:8080/drivers/", requestCreateDriver1,
				Driver.class, Collections.EMPTY_MAP);

		@SuppressWarnings({ "unused", "unchecked" })
		ResponseEntity<Driver> responseCreate2 = restTemplate.postForEntity(
				"http://localhost:8080/drivers/", requestCreateDriver2,
				Driver.class, Collections.EMPTY_MAP);

		@SuppressWarnings({ "unused", "unchecked" })
		ResponseEntity<String> response1 = restTemplate.postForEntity(
				"http://localhost:8080/drivers/status", requestBody1,
				String.class, Collections.EMPTY_MAP);

		@SuppressWarnings({ "unused", "unchecked" })
		ResponseEntity<String> response2 = restTemplate.postForEntity(
				"http://localhost:8080/drivers/status", requestBody1,
				String.class, Collections.EMPTY_MAP);

		@SuppressWarnings({ "unchecked" })
		ResponseEntity<List> responseStatus = restTemplate
				.getForEntity(
						"http://localhost:8080/drivers/inArea?sw=-23.612474,-46.702746&ne=-23.589548,-46.673392",
						List.class, Collections.EMPTY_MAP);
		final List<Driver> entries = responseStatus.getBody();
		// Then
		assertTrue(entries.size() == 2);
		assertNotNull(responseStatus);
		assertEquals(HttpStatus.OK, responseStatus.getStatusCode());

	}

}
