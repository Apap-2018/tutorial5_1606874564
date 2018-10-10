package com.apap.tutorial5.service;
import java.util.List;

import com.apap.tutorial5.model.CarModel;

public interface CarService {
	void addCar(CarModel car);
	void deleteCar(Long id);
	void updateCar(Long id, CarModel car);
	CarModel getCar(Long id);
}
