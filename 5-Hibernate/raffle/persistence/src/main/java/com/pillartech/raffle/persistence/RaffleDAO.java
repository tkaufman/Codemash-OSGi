package com.pillartech.raffle.persistence;

import java.util.List;

import com.pillartech.raffle.domain.Raffle;

public interface RaffleDAO
{
	public Raffle create(Raffle r);
	public Raffle update(Raffle r);
	public void delete(Raffle r);
	public List<Raffle> findAll();
}

