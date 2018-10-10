package com.apap.tutorial5.service;
import java.util.List;
import com.apap.tutorial5.model.CarModel;
import com.apap.tutorial5.repository.CarDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarServiceImpl implements CarService{
	@Autowired
	private CarDb carDb;
	
	@Override
	public void addCar(CarModel car) {
		carDb.save(car);
	}
	
	@Override
	public void deleteCar(Long id) {
		carDb.deleteById(id);
	}
	
	@Override
	public void updateCar(Long id, CarModel newCar) {
		CarModel carUpdated = carDb.getOne(id);
		carUpdated.setAmount(newCar.getAmount());
		carUpdated.setBrand(newCar.getBrand());
		carUpdated.setType(newCar.getType());
		carUpdated.setPrice(newCar.getPrice());
		carDb.save(carUpdated);	
	}
	
	@Override
	public CarModel getCar(Long id) {
		return carDb.findById(id).get();
	}

}
