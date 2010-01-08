package com.pillartech.raffle.service;

import java.util.List;
import java.util.Set;

import com.pillartech.raffle.domain.Raffle;


public interface RaffleService {

	public List<Raffle> currentRaffles();
	public Raffle createRaffle(String name);
	public Raffle findRaffle(Long id);
	public void addEntry(Long raffleId, String name, String email);
	public Set<String> pickWinners(Long raffleId, int numOfWinners);
}
