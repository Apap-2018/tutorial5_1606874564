package com.apap.tutorial5.controller;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.apap.tutorial5.model.*;
import com.apap.tutorial5.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.apap.tutorial5.service.DealerService;

@Controller
public class DealerController{
	@Autowired
	private DealerService dealerService;
	
	@Autowired
	private CarService carService;
	
	@RequestMapping("/")
	private String home(Model model) {
		model.addAttribute("title", "Home");
		return "home";
	}
	
	@RequestMapping(value = "/dealer/add", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("dealer", new DealerModel());
		model.addAttribute("title", "Add Dealer");
		return "addDealer";
	}
	
	@RequestMapping(value = "/dealer/add", method = RequestMethod.POST)
	private String addDealerSubmit(@ModelAttribute DealerModel dealer, Model model) {
		dealerService.addDealer(dealer);
		model.addAttribute("title", "Add Dealer");
		return "add";
	}
	
	@RequestMapping(value="/dealer/delete", method = RequestMethod.GET)
	private String deleteDealer(@RequestParam("dealerId") Long dealerId, Model model){
		if(dealerService.getDealerDetailById(dealerId).isPresent()) {
			DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
			dealerService.deleteDealer(dealer);
			model.addAttribute("title", "Delete Dealer");
			return "delete-dealer";
		}
		return "error";
	}
	
	@RequestMapping(value= "/dealer/view", method = RequestMethod.GET)
	private String viewDealer(@RequestParam("dealerId") Long dealerId, Model model) {
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		List<CarModel> listCar = dealer.getListcar();
		Collections.sort(listCar, comparePrice);
		dealer.setListcar(listCar);
		model.addAttribute("dealer", dealer);
		model.addAttribute("title", "View Dealer");
		return "view-dealer";
	}
	
	@RequestMapping(value= "/dealer/update/{id}", method = RequestMethod.GET)
	private String updateDealer(@PathVariable(value="id") Long dealerId, Model model) {
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		model.addAttribute("dealer", dealer);
		model.addAttribute("title", "Update Dealer");
		return "change-dealer";
		
	}
	
	@RequestMapping(value= "/dealer/update/{id}", method = RequestMethod.POST)
	private String updateSubmitDealer(@PathVariable(value="id") Long dealerId, @ModelAttribute Optional<DealerModel> dealer, Model model) {
		if(dealer.isPresent()) {
			dealerService.updateDealer(dealerId, dealer);
			return "update";
		}
		model.addAttribute("title", "Error");
		return "error";
	}
	
	@RequestMapping(value="dealer/view-all", method = RequestMethod.GET)
	private String viewAll(Model model) {
		List<DealerModel> cars = dealerService.getAllDetailDealer();
		model.addAttribute("dealer", cars);
		model.addAttribute("title", "View All");
		return "view-all";
	}
	
	public static Comparator<CarModel> comparePrice = new Comparator<CarModel>() {
		public int compare(CarModel o1, CarModel o2) {
			Long price1 = o1.getPrice();
			Long price2 = o2.getPrice();
			
			return price1.compareTo(price2);
		}
	};
	
}
