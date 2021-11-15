package simonw.carwebdev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import simonw.carwebdev.exception.CarNotFoundException;
import simonw.carwebdev.model.Car;
import simonw.carwebdev.service.CarService;

@RestController
@RequestMapping("cars")
public class CarController {
	
	private CarService carService;
	
	@Autowired
	public CarController(CarService carService) {
		this.carService = carService;
	}

	@PostMapping("/")
	public Car addCar(@RequestBody Car newCar) {
		return carService.addCar(newCar);
	}
	

	@GetMapping(value = "/{carId}", produces = "application/json")	
	public Car retrieveCar(@PathVariable Long carId) throws CarNotFoundException {		
		return carService.getCarById(carId);
	}
	
	@DeleteMapping("/")
	public void deleteCar(Long carId) {
		carService.deleteCarById(carId);
	}
	
	@ExceptionHandler(CarNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	void onCarNotFoundException(CarNotFoundException exception) {
	    // TODO get message into body
	}
}
