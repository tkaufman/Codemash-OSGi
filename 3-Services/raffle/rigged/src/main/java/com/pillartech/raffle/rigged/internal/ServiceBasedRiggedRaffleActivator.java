package com.pillartech.raffle.rigged.internal;

import java.util.Set;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import com.pillartech.raffle.service.RaffleService;

public final class ServiceBasedRiggedRaffleActivator implements BundleActivator {

	private ServiceTracker tracker;
	private RaffleService service;
	
	public void start(BundleContext bc) throws Exception {
		System.out.println("Grabbing a handle on the raffle service");
		tracker = new ServiceTracker(bc, RaffleService.class.getName(), null);
		tracker.open();
		
		service = (RaffleService) tracker.getService();
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

	public void stop(BundleContext bc) throws Exception {
		System.out.println("And the winner of the raffle is ...");
		Set<String> winners = service.pickWinners(1);
		for (String winner : winners) {
			System.out.println(winner + " won!");
		}
		
		System.out.println("Releasing handle on the raffle service.");
		tracker.close();
	}
}
