package simonw.carwebdev.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import simonw.carwebdev.exception.CarNotFoundException;
import simonw.carwebdev.model.Car;
import simonw.carwebdev.repository.CarRespository;

@Service
public class CarServiceImpl implements CarService {
	
	private CarRespository carRespository;

	@Autowired
	public CarServiceImpl(CarRespository carRespository) {
		this.carRespository = carRespository;
	}
	
	@Override
	public Car addCar(Car newCar) {
		return carRespository.save(newCar);
	}

	@Override
	public Car getCarById(Long id) throws CarNotFoundException{
		Optional<Car> car = carRespository.findById(id);
		if (car.isEmpty()) {
			throw new CarNotFoundException("Car with Id " + id + " not found in database.");
		}
		return car.get();
	}


	@Override
	public void deleteCarById(Long id) {
		carRespository.deleteById(id);		
	}
	
	@Override
	public Car updateCar(Car updatedCar) throws CarNotFoundException{
		if (carRespository.existsById(updatedCar.getId())) {
			return carRespository.save(updatedCar);
		} else {
			throw new CarNotFoundException("Car with Id " + updatedCar.getId() + " not found in database.");
		}
	}
}
