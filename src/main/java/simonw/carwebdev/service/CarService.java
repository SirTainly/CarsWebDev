package simonw.carwebdev.service;

import simonw.carwebdev.exception.CarNotFoundException;
import simonw.carwebdev.model.Car;

public interface CarService {

	Car addCar(Car newCar);
	
	Car getCarById(Long id) throws CarNotFoundException;
	
	void deleteCarById(Long id);
}
