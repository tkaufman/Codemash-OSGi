package com.pillartech.raffle.web;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pillartech.raffle.domain.Raffle;
import com.pillartech.raffle.service.RaffleService;

@Controller
@RequestMapping("/winners.htm")
public class WinnersController {

	@Autowired
	RaffleService raffleService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String getRaffles(ModelMap model) {
		List<Raffle> raffles = raffleService.currentRaffles();
		model.addAttribute("raffles", raffles);
		return "pickWinnersForm";
	}

	@RequestMapping(method=RequestMethod.POST)
	public String pickWinners(Long raffleId, int numWinners, ModelMap model) {
		Set<String> winners = raffleService.pickWinners(raffleId, numWinners);
		for(String w: winners) {
			System.out.println("Winner: " + w);
		}
		model.addAttribute("winners", winners);
		return "winners";
	}

}
