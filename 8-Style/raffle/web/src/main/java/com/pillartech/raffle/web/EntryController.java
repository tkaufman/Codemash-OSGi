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
@RequestMapping("/entries.htm")
public class EntryController {

	@Autowired
	RaffleService raffleService;
	
	@RequestMapping(method=RequestMethod.POST)
	public String addEntryToRaffle(Long raffleId, String entryName, String entryEmail, ModelMap model) {
		raffleService.addEntry(raffleId, entryName, entryEmail);
		Raffle r = raffleService.findRaffle(raffleId);
		model.addAttribute("raffle", r);
		model.addAttribute("message", "Successfully added " + entryName);
		return "entriesForm";
	}

}
