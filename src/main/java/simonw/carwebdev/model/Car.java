package simonw.carwebdev.model;

import lombok.Data;

@Data
public class Car {
	private Long id;
	private String make;
	private String model;
	private String colour;
	private Integer year;
}
