package com.pillartech.raffle.service.internal;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.pillartech.raffle.domain.Entry;
import com.pillartech.raffle.domain.Raffle;
import com.pillartech.raffle.persistence.EntryDAO;
import com.pillartech.raffle.persistence.RaffleDAO;
import com.pillartech.raffle.service.RaffleService;

public class RaffleServiceImpl implements RaffleService {

	private Raffle raffle = null;
	private RaffleDAO raffleDAO = null;
	private EntryDAO entryDAO = null;

	public RaffleServiceImpl(RaffleDAO rDAO, EntryDAO eDAO) {
		raffleDAO = rDAO;
		entryDAO = eDAO;		
	}


	@Transactional
	public void addEntry(String name, String email) {
		if (raffle == null) {
			raffle = new Raffle();
			raffleDAO.create(raffle);
		}
		Entry e = new Entry();
		e.setName(name);
		e.setEmail(email);
		e.setCreated(new Date());
		
		raffle.addEntry(e);
		entryDAO.create(e);
	}

	@Transactional
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
