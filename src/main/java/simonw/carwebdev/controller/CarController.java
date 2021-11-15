package simonw.carwebdev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import simonw.carwebdev.exception.CarNotFoundException;
import simonw.carwebdev.model.Car;
import simonw.carwebdev.service.CarService;

@RestController
@RequestMapping("cars")
public class CarController {

	//TODO add validation on the car objects
	
	private CarService carService;

	@Autowired
	public CarController(CarService carService) {
		this.carService = carService;
	}

	@PostMapping("/")
	public Car addCar(@RequestBody Car newCar) {
		return carService.addCar(newCar);
	}

	@PutMapping("/{carId}")
	public void updateCar(@PathVariable Long carId, @RequestBody Car updatedCar) {
		//TODO sort out if we should use the path param or the object id for the update
		try {
			updatedCar.setId(carId);
			carService.updateCar(updatedCar);
		} catch (CarNotFoundException carex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, carex.getMessage());
		}
	}

	@GetMapping(value = "/{carId}", produces = "application/json")
	public Car retrieveCar(@PathVariable Long carId) {
		try {			
			return carService.getCarById(carId);
		} catch (CarNotFoundException carex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, carex.getMessage(), carex);
		}

	}

	@DeleteMapping("/{carId}")
	public void deleteCar(@PathVariable Long carId) {
		carService.deleteCarById(carId);
	}

}
