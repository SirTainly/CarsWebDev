package simonw.carwebdev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import simonw.carwebdev.model.Car;

@SpringBootTest
public class CarAPITest {

	private String SERVICE_URL = "http://localhost:8080/cars/";

	@Autowired
	private RestTemplate restTemplate;

	private static Car car;

	@BeforeAll
	public static void setup() {
		car = new Car();
		car.setColour("Red");
		car.setMake("Ford");
		car.setModel("Fiesta");
		car.setYear(1983);
	}

	@Test
	public void testAllCarFunctions() throws Exception {

		// Not ideal, as it should be one test per operation, but for brevity and
		// avoiding setup code.

		ResponseEntity<Car> createdCarResponse = restTemplate.postForEntity(SERVICE_URL, car, Car.class);
		Car createdCar = createdCarResponse.getBody();

		assertNotNull(createdCar.getId());
		assertEquals("Red", createdCar.getColour());
		assertEquals("Ford", createdCar.getMake());
		assertEquals("Fiesta", createdCar.getModel());
		assertEquals(1983, createdCar.getYear());

		ResponseEntity<Car> retrievedCarResponse = restTemplate.getForEntity(SERVICE_URL + createdCar.getId(),
				Car.class);
		Car retrievedCar = retrievedCarResponse.getBody();

		assertEquals(createdCar, retrievedCar);

		Car updatedCar = new Car();
		updatedCar.setColour("Blue");
		updatedCar.setMake("Vauxhall");
		updatedCar.setModel("Vestra");
		updatedCar.setYear(1996);
		updatedCar.setId(retrievedCar.getId());

		HttpEntity<Car> requestEntity = new HttpEntity<Car>(updatedCar, new HttpHeaders());
		restTemplate.exchange(SERVICE_URL + updatedCar.getId(), HttpMethod.PUT, requestEntity, Car.class,
				new HashMap<String, String>());

		ResponseEntity<Car> retrievedUpdatedCarResponse = restTemplate.getForEntity(SERVICE_URL + updatedCar.getId(),
				Car.class);
		assertEquals(updatedCar, retrievedUpdatedCarResponse.getBody());
		
		HttpClientErrorException updateException = assertThrows(HttpClientErrorException.class, () -> {
			restTemplate.exchange(SERVICE_URL + "100", HttpMethod.PUT, requestEntity, Car.class,
					new HashMap<String, String>());
		});
		
		assertEquals(HttpStatus.NOT_FOUND.value(), updateException.getRawStatusCode());

		restTemplate.delete(SERVICE_URL + createdCar.getId());

		HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
			restTemplate.getForEntity(SERVICE_URL + createdCar.getId(), Car.class);
		});
		
		assertEquals(HttpStatus.NOT_FOUND.value(), exception.getRawStatusCode());
	}

}
