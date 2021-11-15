package simonw.carwebdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import simonw.carwebdev.model.Car;

@Repository
public interface CarRespository extends JpaRepository<Car, Long> {

}

