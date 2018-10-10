package com.apap.tutorial5.controller;
import com.apap.tutorial5.model.*;
import com.apap.tutorial5.service.*;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CarController {
	@Autowired
	private CarService carService;
	
	@Autowired
	private DealerService dealerService;
	
	
	@RequestMapping(value = "/car/add/{dealerId}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "dealerId") Long dealerId, Model model) {
		ArrayList<CarModel> listOfCars = new ArrayList<CarModel>();
		listOfCars.add(new CarModel());
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		dealer.setListcar(listOfCars);
		model.addAttribute("title", "Home");
		model.addAttribute("dealer", dealer);
		return "addCar";
	}
	
	@RequestMapping(value = "/car/add/{dealerId}", params= {"add"}, method = RequestMethod.POST)
	public String addRow(@ModelAttribute DealerModel dealer, Model model) {
		if (dealer.getListcar() == null) {
			dealer.setListcar(new ArrayList<CarModel>());
		}
		dealer.getListcar().add(new CarModel());
		
		model.addAttribute("dealer", dealer);
		return "addCar";
	}
	
	@RequestMapping(value="/car/add/{dealerId}", params= {"delete"}, method = RequestMethod.POST)
	public String removeRow(@ModelAttribute DealerModel dealer, Model model, HttpServletRequest req) {
		int index = Integer.parseInt(req.getParameter("delete"));
		dealer.getListcar().remove(index);
		model.addAttribute("dealer", dealer);
		return "addCar";
	}
	
	@RequestMapping(value = "/car/add/{dealerId}", params= {"save"}, method = RequestMethod.POST)
	private String addCarSubmit(@ModelAttribute DealerModel dealer, Model model) {
		DealerModel getDealer = dealerService.getDealerDetailById(dealer.getId()).get();
		for(CarModel car : dealer.getListcar()) {
			car.setDealer(getDealer);
			carService.addCar(car);
		}
		
		model.addAttribute("title", "Add Car");
		return "add";
	}
	
	@RequestMapping(value = "/car/delete", method = RequestMethod.POST)
	private String deleteCar(@ModelAttribute DealerModel dealer, Model model) {
		for(CarModel car : dealer.getListcar()) {
			carService.deleteCar(car.getId());
		}
		model.addAttribute("title", "Delete Car");
		return "delete-car";
	}
	
	@RequestMapping(value="/car/update/{id}", method = RequestMethod.GET)
	private String updateCar(@PathVariable(value = "id") Long id, Model model) {
		CarModel car = carService.getCar(id);
		model.addAttribute("car",car);
		model.addAttribute("title", "Update Car");
		return "change-car";
	}
	
	@RequestMapping(value = "/car/update/{id}", method = RequestMethod.POST)
	private String updateCarSubmit(@PathVariable(value = "id") Long id, @ModelAttribute CarModel car, Model model) {
		carService.updateCar(id, car);
		model.addAttribute("title", "Update Car");
		return "update";
	}
}
