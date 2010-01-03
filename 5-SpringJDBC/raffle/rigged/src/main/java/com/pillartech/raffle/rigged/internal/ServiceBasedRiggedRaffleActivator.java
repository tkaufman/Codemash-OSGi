package com.pillartech.raffle.rigged.internal;

import java.util.Set;

import com.pillartech.raffle.service.RaffleService;

public final class ServiceBasedRiggedRaffleActivator {

	private RaffleService service;
	
	public RaffleService getRaffleService() {
		return service;
	}
	
	public void setRaffleService(RaffleService svc) {
		service = svc;
	}
	
	public void start() throws Exception {
		if (service != null) {
			addEntrants();
		}
		else {
			System.out.println("Unable to rig the raffle, cannot get a handle on the service");
		}
	}

	private void addEntrants() {
		System.out.println("Adding entrants");
		final int COUNT = 10;
		for (int i = 0; i < COUNT; i++) {
			service.addEntry("Todd("+i+")", "toddkaufman@gmail.com");
		}
		System.out.println(COUNT + " entries added to the raffle.");
	}

	public void stop() throws Exception {
		System.out.println("And the winner of the raffle is ...");
		Set<String> winners = service.pickWinners(1);
		for (String winner : winners) {
			System.out.println(winner + " won!");
		}
	}
}
