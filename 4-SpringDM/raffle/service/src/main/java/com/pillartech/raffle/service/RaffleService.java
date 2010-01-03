package com.pillartech.raffle.service;

import java.util.Set;


public interface RaffleService {

	public void addEntry(String name, String email);
	public Set<String> pickWinners(int numOfWinners);
}
