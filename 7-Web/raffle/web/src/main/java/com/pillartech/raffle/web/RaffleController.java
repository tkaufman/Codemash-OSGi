package com.pillartech.raffle.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pillartech.raffle.domain.Raffle;
import com.pillartech.raffle.service.RaffleService;

@Controller
@RequestMapping("/raffle.htm")
public class RaffleController {

	@Autowired
	RaffleService raffleService;
	
	@RequestMapping(method=RequestMethod.POST)
	public String loadRaffle(Long raffleId, ModelMap model) {
		Raffle r = raffleService.findRaffle(raffleId);
		model.addAttribute("raffle", r);
		return "entriesForm";
	}

}
