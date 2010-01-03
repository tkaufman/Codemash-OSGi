package com.pillartech.raffle.service.internal;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.pillartech.raffle.domain.Entry;
import com.pillartech.raffle.domain.Raffle;
import com.pillartech.raffle.persistence.EntryDAO;
import com.pillartech.raffle.persistence.RaffleDAO;
import com.pillartech.raffle.service.RaffleService;

public class RaffleServiceImpl implements RaffleService {

	private RaffleDAO raffleDAO = null;
	private EntryDAO entryDAO = null;

	public RaffleServiceImpl(RaffleDAO rDAO, EntryDAO eDAO) {
		raffleDAO = rDAO;
		entryDAO = eDAO;		
	}


	public List<Raffle> currentRaffles() {
		return raffleDAO.findAll();
	}

	public Raffle findRaffle(Long id) {
		return raffleDAO.findById(id);
	}

	@Transactional
	public Raffle createRaffle(String name) {
		Raffle r = new Raffle();
		r.setName(name);
		raffleDAO.create(r);
		return r;
	}

	@Transactional
	public void addEntry(Long raffleId, String name, String email) {
		Raffle raffle;
		if (raffleId == null) {
			raffle = new Raffle();
			raffleDAO.create(raffle);
		}
		else {
			raffle = raffleDAO.findById(raffleId);
		}
		Entry e = new Entry();
		e.setName(name);
		e.setEmail(email);
		e.setCreated(new Date());
		e.setRaffle(raffle);
		entryDAO.create(e);
	}

	@Transactional
	public Set<String> pickWinners(Long raffleId, int numOfWinners) {
		Raffle raffle = raffleDAO.findById(raffleId);
		List<Entry> entries = entryDAO.findForRaffle(raffleId);
		for (Entry e: entries) {
			raffle.addEntry(e);
		}
		raffle.setNumberOfWinners(numOfWinners);
		Set<Entry> winners = raffle.pickWinners();
		
		Set<String> winnerNames = new HashSet<String>();
		for (Entry entry : winners) {
			winnerNames.add(entry.getName());
		}
		return winnerNames;
	}

}
