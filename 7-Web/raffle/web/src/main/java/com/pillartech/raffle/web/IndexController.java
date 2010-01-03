package com.pillartech.raffle.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pillartech.raffle.domain.Raffle;
import com.pillartech.raffle.service.RaffleService;

@Controller
@RequestMapping("/index.htm")
public class IndexController {

	@Autowired
	RaffleService raffleService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String showRaffleForm(ModelMap model) {
		List<Raffle> raffles = raffleService.currentRaffles();
		model.addAttribute("raffles", raffles);
		return "raffleForm";
	}

	@RequestMapping(method=RequestMethod.POST)
	public String createRaffle(String raffleName, ModelMap model) {
		Raffle r = raffleService.createRaffle(raffleName);
		model.addAttribute("raffle", r);
		return "entriesForm";
	}

}
