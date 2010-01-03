package com.pillartech.raffle.service.internal;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.pillartech.raffle.domain.Entry;
import com.pillartech.raffle.domain.Raffle;
import com.pillartech.raffle.service.RaffleService;

public class RaffleServiceImpl implements RaffleService {

	private Raffle raffle = null;

	public RaffleServiceImpl() {
		raffle = new Raffle();
	}

	public void addEntry(String name, String email) {
		Entry e = new Entry();
		e.setName(name);
		e.setEmail(email);
		e.setCreated(new Date());
		
		raffle.addEntry(e);
	}

	public Set<String> pickWinners(int numOfWinners) {
		raffle.setNumberOfWinners(numOfWinners);
		Set<Entry> winners = raffle.pickWinners();
		
		Set<String> winnerNames = new HashSet<String>();
		for (Entry entry : winners) {
			winnerNames.add(entry.getName());
		}
		return winnerNames;
	}

}
